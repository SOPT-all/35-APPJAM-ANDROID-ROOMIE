package com.wearerommies.roomie.data.datasource.interceptor

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.jakewharton.processphoenix.ProcessPhoenix
import com.wearerommies.roomie.data.datalocal.datasource.TokenDataSource
import com.wearerommies.roomie.data.datasource.AuthDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataSource: TokenDataSource,
    private val authDataSource: AuthDataSource,
    @ApplicationContext private val context: Context,
) : Interceptor {
    private val mutex = Mutex()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest =
            if (dataSource.accessToken.isNotBlank()) originalRequest.addAuthorizationHeader() else originalRequest
        var response = chain.proceed(authRequest)

        when (response.code) {
            TOKEN_EXPIRED -> {
                response.close()
                response = handleTokenExpiration(
                    chain = chain,
                    originalRequest = originalRequest,
                    requestAccessToken = dataSource.accessToken
                )
            }
        }

        return response
    }

    private fun Request.addAuthorizationHeader() =
        this.newBuilder().addHeader(AUTHORIZATION, "$BEARER ${dataSource.accessToken}").build()

    private fun handleTokenExpiration(
        chain: Interceptor.Chain,
        originalRequest: Request,
        requestAccessToken: String
    ): Response = runBlocking {
        mutex.withLock {
            when (isTokenValid(
                requestAccessToken = requestAccessToken,
                currentAccessToken = dataSource.accessToken
            )) {
                true -> chain.proceed(originalRequest.addAuthorizationHeader())
                false -> handleTokenRefresh(
                    chain = chain,
                    originalRequest = originalRequest,
                    refreshToken = dataSource.refreshToken
                )
            }
        }
    }

    private fun isTokenValid(requestAccessToken: String, currentAccessToken: String): Boolean =
        requestAccessToken != currentAccessToken && currentAccessToken.isNotBlank()

    private fun handleTokenRefresh(
        chain: Interceptor.Chain,
        originalRequest: Request,
        refreshToken: String
    ): Response {
        val result = runCatching {
            runBlocking {
                authDataSource.postTokenReissue("$BEARER $refreshToken")
            }
        }

        return result.fold(
            onSuccess = { response ->
                val newAccessToken = response.data.accessToken

                if (newAccessToken.isBlank()) {
                    return handleTokenRefreshFailed()
                }

                with(dataSource) {
                    accessToken = newAccessToken
                }

                Timber.tag("tokenReissue").d("Token reissue success: $newAccessToken")

                chain.proceed(originalRequest.addAuthorizationHeader())
            },
            onFailure = { error ->
                Timber.e("Token reissue failed: $error")
                handleTokenRefreshFailed()
            }
        )
    }

    private fun handleTokenRefreshFailed(): Response {
        Handler(Looper.getMainLooper()).post {
            ProcessPhoenix.triggerRebirth(context)
        }

        dataSource.clearInfo()

        throw IllegalStateException("Token reissue failed. Restarting app.")
    }

    companion object {
        private const val TOKEN_EXPIRED = 401
        private const val BEARER = "Bearer"
        private const val AUTHORIZATION = "Authorization"
    }
}