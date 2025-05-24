package com.wearerommies.roomie.presentation.ui.mypage

sealed class MySideEffect {
    data class ShowToast(val message: String) : MySideEffect()
    data object NavigateToBookMark: MySideEffect()
    data object NavigateToMyAccount: MySideEffect()
}