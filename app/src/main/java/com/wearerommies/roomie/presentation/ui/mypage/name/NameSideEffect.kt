package com.wearerommies.roomie.presentation.ui.mypage.name

sealed class NameSideEffect {
    data class ShowToast(val message: String) : NameSideEffect()
}
