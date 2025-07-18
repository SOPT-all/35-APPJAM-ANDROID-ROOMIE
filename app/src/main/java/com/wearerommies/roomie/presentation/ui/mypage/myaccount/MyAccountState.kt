package com.wearerommies.roomie.presentation.ui.mypage.myaccount

import com.wearerommies.roomie.domain.entity.AccountEntity

data class MyAccountState(
    val uiState: AccountEntity = AccountEntity(
        nickname = "닉네임",
        socialType = "KAKAO",
        birthDate = "",
        gender = "",
        name = "",
        phoneNumber = ""
    ),
    val isShowLogoutDialog: Boolean = false,
    val isShowWithdrawDialog: Boolean = false,
)
