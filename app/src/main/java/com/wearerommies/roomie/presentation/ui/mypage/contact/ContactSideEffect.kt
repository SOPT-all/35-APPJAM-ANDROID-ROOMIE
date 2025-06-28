package com.wearerommies.roomie.presentation.ui.mypage.contact

sealed class ContactSideEffect {
    data object NavigateUp : ContactSideEffect()
}
