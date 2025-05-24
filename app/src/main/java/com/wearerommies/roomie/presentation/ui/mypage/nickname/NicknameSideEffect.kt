package com.wearerommies.roomie.presentation.ui.mypage.nickname

sealed class NicknameSideEffect {
    data class ShowToast(val message: String) : NicknameSideEffect()
}
