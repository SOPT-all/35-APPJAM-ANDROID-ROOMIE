package com.wearerommies.roomie.presentation.ui.mypage.myaccount

sealed class MyAccountSideEffect {
    data class ShowToast(val message: String) : MyAccountSideEffect()
    data object NavigateToName : MyAccountSideEffect()
    data object NavigateToNickname : MyAccountSideEffect()
    data object NavigateToBirth : MyAccountSideEffect()
    data object NavigateToGender : MyAccountSideEffect()
    data object NavigateToContact : MyAccountSideEffect()
}