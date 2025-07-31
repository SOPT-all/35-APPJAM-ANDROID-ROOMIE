package com.wearerommies.roomie.presentation.ui.tour.completed

sealed class TourCompletedSideEffect {
    data object NavigateUp : TourCompletedSideEffect()
    data object NavigateToHome : TourCompletedSideEffect()
    data object NavigateToMap : TourCompletedSideEffect()
}
