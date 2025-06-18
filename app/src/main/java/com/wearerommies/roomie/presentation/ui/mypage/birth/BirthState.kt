package com.wearerommies.roomie.presentation.ui.mypage.birth

import com.wearerommies.roomie.domain.entity.TourEntity

data class BirthState(
    //todo: entity 수정
    val uiState: TourEntity = TourEntity(),
    val birth: String = "",
    val isShowBirthDateModal: Boolean = false,
) {
    val isEnabled = uiState.birthDate.isNotEmpty()
}
