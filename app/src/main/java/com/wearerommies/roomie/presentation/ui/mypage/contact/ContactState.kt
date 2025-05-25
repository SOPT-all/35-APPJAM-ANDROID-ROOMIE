package com.wearerommies.roomie.presentation.ui.mypage.contact

import com.wearerommies.roomie.domain.entity.TourEntity

data class ContactState(
    //todo: entity 수정
    val uiState: TourEntity = TourEntity(),
    val isValidated: Boolean = true
) {
    val isEnabled = uiState.phoneNumber.isNotEmpty()
}
