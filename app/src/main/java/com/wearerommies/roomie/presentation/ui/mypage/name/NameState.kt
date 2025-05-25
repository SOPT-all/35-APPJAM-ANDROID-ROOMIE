package com.wearerommies.roomie.presentation.ui.mypage.name

import com.wearerommies.roomie.domain.entity.TourEntity

data class NameState(
    //todo: entity 변경
    val uiState: TourEntity = TourEntity(),
    val isValidated: Boolean = true
) {
    val isEnabled = uiState.name.isNotEmpty()
}
