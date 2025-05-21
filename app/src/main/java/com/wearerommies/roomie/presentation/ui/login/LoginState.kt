package com.wearerommies.roomie.presentation.ui.login

data class LoginState(
    val isLoggedIn: Boolean = false,
    val accessToken: String? = null,
    val nickname: String? = null,
    val errorMessage: String? = null
)