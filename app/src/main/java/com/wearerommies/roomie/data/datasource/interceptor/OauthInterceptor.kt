package com.wearerommies.roomie.data.datasource.interceptor

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.jakewharton.processphoenix.ProcessPhoenix
import com.wearerommies.roomie.BuildConfig
import com.wearerommies.roomie.data.datalocal.datasource.TokenDataSource
import com.wearerommies.roomie.data.dto.response.BaseResponse
import com.wearerommies.roomie.data.dto.response.ResponseReissueTokenDto
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class OauthInterceptor @Inject constructor(
    private val json: Json,
    private val dataSource: TokenDataSource,
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
    ): Response =
        tryReissueToken(
            chain = chain,
            originalRequest = originalRequest,
            refreshToken = refreshToken
        ).let { refreshTokenResponse ->
            Timber.d("handleTokenRefresh $refreshToken")
            when (refreshTokenResponse.isSuccessful) {
                true -> handleTokenRefreshSuccess(
                    chain = chain,
                    originalRequest = originalRequest,
                    refreshTokenResponse = refreshTokenResponse
                )

                false -> handleTokenRefreshFailed(refreshTokenResponse = refreshTokenResponse)
            }
        }


    private fun tryReissueToken(
        chain: Interceptor.Chain,
        originalRequest: Request,
        refreshToken: String
    ): Response = chain.proceed(
        originalRequest.newBuilder()
            .post("".toRequestBody(null))
            .url("${BuildConfig.BASE_URL}/v1/auth/oauth/reissue")
            .addHeader(AUTHORIZATION, "$BEARER $refreshToken")
            .build()
    )

    private fun handleTokenRefreshSuccess(
        chain: Interceptor.Chain,
        originalRequest: Request,
        refreshTokenResponse: Response
    ): Response {
        val bodyString = refreshTokenResponse.body?.string()

        if (bodyString.isNullOrBlank()) {
            Timber.e("Token refresh response body is empty.")
            throw IllegalStateException("Empty response body during token reissue")
        }

        val responseAccessToken =
            json.decodeFromString<BaseResponse<ResponseReissueTokenDto>>(bodyString)

        Timber.tag("tokenReissue").d("Token reissue success: $responseAccessToken")

        with(dataSource) {
            accessToken = responseAccessToken.data.accessToken
        }

        refreshTokenResponse.close()

        return chain.proceed(originalRequest.addAuthorizationHeader())
    }


    private fun handleTokenRefreshFailed(refreshTokenResponse: Response): Response {
        Timber.tag("tokenReissue").e("Token reissue fail $refreshTokenResponse")

        refreshTokenResponse.close()

        Handler(Looper.getMainLooper()).post {
            ProcessPhoenix.triggerRebirth(context)
        }

        dataSource.clearInfo()

        return refreshTokenResponse
    }

    companion object {
        private const val TOKEN_EXPIRED = 401
        private const val BEARER = "Bearer"
        private const val AUTHORIZATION = "Authorization"
    }
}