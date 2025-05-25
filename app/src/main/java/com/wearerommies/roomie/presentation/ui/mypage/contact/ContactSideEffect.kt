package com.wearerommies.roomie.presentation.ui.mypage.contact

sealed class ContactSideEffect {
    data class ShowToast(val message: String) : ContactSideEffect()
}
