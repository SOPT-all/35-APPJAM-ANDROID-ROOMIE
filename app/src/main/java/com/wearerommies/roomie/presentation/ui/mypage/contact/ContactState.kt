package com.wearerommies.roomie.presentation.ui.mypage.contact

data class ContactState(
    val phoneNumber: String = "",
    val updatedPhoneNumber: String = "",
    val isValidated: Boolean = true
) {
    val isEnabled = updatedPhoneNumber.isNotEmpty() && phoneNumber != updatedPhoneNumber && isValidated
}
