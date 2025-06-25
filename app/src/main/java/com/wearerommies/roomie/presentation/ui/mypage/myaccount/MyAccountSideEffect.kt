package com.wearerommies.roomie.presentation.ui.mypage.myaccount

sealed class MyAccountSideEffect {
    data class NavigateToName(val name: String) : MyAccountSideEffect()
    data class NavigateToNickname(val nickname: String) : MyAccountSideEffect()
    data class NavigateToBirth(val birth: String) : MyAccountSideEffect()
    data class NavigateToGender(val gender: String) : MyAccountSideEffect()
    data class NavigateToContact(val contact: String) : MyAccountSideEffect()
}