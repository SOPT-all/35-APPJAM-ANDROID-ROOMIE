package com.wearerommies.roomie.presentation.ui.mypage.my

import com.wearerommies.roomie.domain.entity.MyPageEntity

data class MyState(
    val uiState: MyPageEntity = MyPageEntity(
        nickname = "닉네임",
        socialType = "KAKAO"
    )
)
