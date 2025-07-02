package com.wearerommies.roomie.presentation.ui.mypage.birth

data class BirthState(
    val birth: String = "",
    val updatedBirth: String = "",
    val isShowBirthDateModal: Boolean = false,
) {
    val isEnabled = updatedBirth.isNotEmpty() && birth != updatedBirth
}
