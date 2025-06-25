package com.wearerommies.roomie.presentation.ui.mypage.myaccount

import com.wearerommies.roomie.domain.entity.MyPageEntity

data class MyAccountState(
    val uiState: MyPageEntity = MyPageEntity(
        nickname = "닉네임",
        socialType = "KAKAO"
    )
)
