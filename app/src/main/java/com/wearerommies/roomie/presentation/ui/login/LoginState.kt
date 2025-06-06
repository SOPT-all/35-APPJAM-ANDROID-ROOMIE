package com.wearerommies.roomie.presentation.ui.login

data class LoginState(
    var isLoggedIn: Boolean = false,
    val accessToken: String? = null,
    val errorMessage: String? = null
)