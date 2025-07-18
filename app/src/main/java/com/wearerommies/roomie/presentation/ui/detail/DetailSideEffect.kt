package com.wearerommies.roomie.presentation.ui.detail

import androidx.annotation.StringRes
import com.wearerommies.roomie.domain.entity.TourEntity

sealed class DetailSideEffect {
    data object NavigateUp : DetailSideEffect()
    data class NavigateDetailRoom(
        val houseId: Long,
        val roomId: Long,
        val title: String
    ) : DetailSideEffect()

    data class NavigateDetailHouse(
        val houseId: Long,
        val title: String
    ) : DetailSideEffect()

    data class NavigateTourApply(
        val tourEntity: TourEntity,
        val houseName: String,
        val roomName: String
    ) : DetailSideEffect()

    data class NavigateToWebView(
        val webViewUrl: String
    ) : DetailSideEffect()

    data class SnackBar(@StringRes val message: Int) : DetailSideEffect()
    data object NavigateToChannel : DetailSideEffect()
}