package com.wearerommies.roomie.presentation.ui.mypage.gender

sealed class GenderSideEffect {
    data class ShowToast(val message: String) : GenderSideEffect()
}
