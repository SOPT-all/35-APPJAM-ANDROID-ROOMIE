package com.wearerommies.roomie.presentation.ui.splash

sealed class SplashSideEffect {
    data object NavigateToOnboarding : SplashSideEffect()
    data class NavigateToHome(val accessToken: String) : SplashSideEffect()
}
