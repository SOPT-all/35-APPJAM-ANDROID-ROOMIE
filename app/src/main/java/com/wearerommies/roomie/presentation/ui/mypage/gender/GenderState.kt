package com.wearerommies.roomie.presentation.ui.mypage.gender

data class GenderState(
    val gender: String = "",
    val updatedGender: String = "",
) {
    val isEnabled = updatedGender.isNotEmpty() && gender != updatedGender
}
