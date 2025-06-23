package com.wearerommies.roomie.presentation.ui.mypage.name

data class NameState(
    val name: String = "",
    val updatedName: String = "",
    val isValidated: Boolean = true
) {
    val isButtonEnabled = updatedName.isNotEmpty() && updatedName != name && isValidated
}
