package com.wearerommies.roomie.presentation.ui.mypage.birth

sealed class BirthSideEffect {
    data class ShowToast(val message: String) : BirthSideEffect()
}
