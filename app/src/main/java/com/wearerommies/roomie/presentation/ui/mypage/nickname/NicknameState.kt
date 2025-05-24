package com.wearerommies.roomie.presentation.ui.mypage.nickname

import com.wearerommies.roomie.domain.entity.MyPageEntity

data class NicknameState(
    val uiState: MyPageEntity = MyPageEntity(
        name = "닉네임"
    )
)
