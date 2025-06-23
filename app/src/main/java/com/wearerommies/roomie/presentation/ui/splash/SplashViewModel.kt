package com.wearerommies.roomie.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearerommies.roomie.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState>
        get() = _state

    private val _sideEffect = MutableSharedFlow<SplashSideEffect>()
    val sideEffect: SharedFlow<SplashSideEffect>
        get() = _sideEffect

    fun checkAutoLogin() =
        viewModelScope.launch {
            delay(SPLASH_SCREEN_DELAY)

            _state.value.isLoggedIn = tokenRepository.getAccessToken().isNotEmpty()

            if (_state.value.isLoggedIn) {
                _sideEffect.emit(SplashSideEffect.NavigateToHome(tokenRepository.getAccessToken()))
            } else
                _sideEffect.emit(SplashSideEffect.NavigateToOnboarding)
        }

    companion object {
        const val SPLASH_SCREEN_DELAY = 2000L
    }
}
