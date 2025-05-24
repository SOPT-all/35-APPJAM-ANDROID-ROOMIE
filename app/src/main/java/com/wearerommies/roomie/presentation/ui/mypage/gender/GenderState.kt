package com.wearerommies.roomie.presentation.ui.mypage.gender

import com.wearerommies.roomie.domain.entity.TourEntity

data class GenderState(
    //todo: entity 수정
    val uiState: TourEntity = TourEntity(),
) {
    val isEnabled = uiState.gender.isNotEmpty()
}
