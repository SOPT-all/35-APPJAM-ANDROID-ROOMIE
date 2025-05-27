package com.wearerommies.roomie.data.di

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.jakewharton.processphoenix.ProcessPhoenix
import com.wearerommies.roomie.R
import com.wearerommies.roomie.data.datalocal.datasource.TokenDataSource
import com.wearerommies.roomie.domain.repository.LoginRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject


class OauthInterceptor @Inject constructor(
    private val loginRepository: LoginRepository,
    private val dataSource: TokenDataSource,
    @ApplicationContext private val context: Context,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest = addAuthorization(originalRequest)

        val response = chain.proceed(authRequest)
        return if (response.code == TOKEN_EXPIRED) {
            handleTokenExpiration(chain, authRequest, response)
        } else {
            response
        }
    }

    private fun addAuthorization(request: Request): Request {
        return if (dataSource.accessToken.isNotBlank()) {
            request.newBuilder().addAuthorizationHeader().build()
        } else {
            request
        }
    }

    private fun Request.Builder.addAuthorizationHeader() =
        this.addHeader(AUTHORIZATION, "$BEARER ${dataSource.accessToken}")

    private fun handleTokenExpiration(
        chain: Interceptor.Chain,
        authRequest: Request,
        response: Response,
    ): Response {
        response.close()

        return if (tryReissueToken()) {
            val newRequest =
                authRequest.newBuilder().removeHeader(AUTHORIZATION).addAuthorizationHeader()
                    .build()
            chain.proceed(newRequest)
        } else {
            clearUserInfoAndRestart()
            chain.proceed(authRequest.newBuilder().build())
        }
    }

    private fun tryReissueToken(): Boolean = runBlocking {
        loginRepository.postTokenReissue("$BEARER ${dataSource.refreshToken}")
    }.onSuccess { data ->
        Timber.d("Successfully reissued token: ${data.refreshToken}")
        updateTokens(data.accessToken, data.refreshToken)
    }.onFailure { error ->
        Timber.e("Failed to reissue token: $error")
    }.isSuccess

    private fun updateTokens(newAccessToken: String, newRefreshToken: String) {
        Timber.e("NEW ACCESS TOKEN : $newAccessToken")
        Timber.e("NEW REFRESH TOKEN : $newRefreshToken")
        dataSource.apply {
            accessToken = newAccessToken
            refreshToken = newRefreshToken
        }
    }

    private fun clearUserInfoAndRestart() {
        dataSource.clearInfo()
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, R.string.relogin_message, Toast.LENGTH_LONG).show()
            ProcessPhoenix.triggerRebirth(context)
        }
    }

    companion object {
        private const val TOKEN_EXPIRED = 40100
        private const val BEARER = "Bearer"
        private const val AUTHORIZATION = "Authorization"
    }
}