package com.wearerommies.roomie.presentation.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.wearerommies.roomie.domain.entity.SocialLoginEntity
import com.wearerommies.roomie.domain.repository.LoginRepository
import com.wearerommies.roomie.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState>
        get() = _state

    private val _sideEffect = MutableSharedFlow<LoginSideEffect>()
    val sideEffect: SharedFlow<LoginSideEffect>
        get() = _sideEffect

    fun startKakaoLogin(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                handleLoginResult(token, error)
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                handleLoginResult(token, error)
            }
        }
    }

    private fun handleLoginResult(token: OAuthToken?, error: Throwable?) {
        viewModelScope.launch {
            if (error != null) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    handleLoginError("로그인 취소")
                } else {
                    handleLoginError("카카오계정으로 로그인 실패: ${error.localizedMessage}")
                }
            } else if (token != null) {
                sendTokenToServer(token.accessToken)
            }
        }
    }

    private fun sendTokenToServer(
        accessToken: String,
        provider: String = KAKAO
    ) {
        viewModelScope.launch {
            loginRepository.postSocialLogin(
                loginData = SocialLoginEntity(
                    accessToken = accessToken,
                    provider = provider
                )
            ).onSuccess { response ->
                tokenRepository.setTokens(response.accessToken, response.refreshToken)
                _sideEffect.emit(LoginSideEffect.LoginSuccess(response.accessToken))
                Timber.tag("sendTokenToServer").d(accessToken)
            }
                .onFailure { error ->
                    val errorMessage = error.localizedMessage ?: "Unknown error"
                    handleLoginError(errorMessage = errorMessage)
                    Timber.tag("sendTokenToServer").e(error)
                }
        }
    }


    private fun handleLoginError(errorMessage: String) {
        viewModelScope.launch {
            _sideEffect.emit(LoginSideEffect.LoginError(errorMessage))
        }
    }

    companion object {
        const val KAKAO = "KAKAO"
    }
}