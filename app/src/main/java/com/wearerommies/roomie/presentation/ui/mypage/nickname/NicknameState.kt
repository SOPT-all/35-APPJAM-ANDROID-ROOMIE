package com.wearerommies.roomie.presentation.ui.mypage.nickname

data class NicknameState(
    val nickname: String = "",
    val updatedNickname: String = "",
    val isValidated: Boolean = true
) {
    val isButtonEnabled = updatedNickname.isNotEmpty() && updatedNickname != nickname && isValidated
}
