package com.wearerommies.roomie.presentation.ui.home

import androidx.annotation.StringRes

sealed class HomeSideEffect {
    data class SnackBar(@StringRes val message: Int) : HomeSideEffect()
    data class BottomSheetSnackBar(@StringRes val message: Int) : HomeSideEffect()
    data object NavigateToBookMark : HomeSideEffect()
    data class NavigateToMood(
        val moodTag: String
    ) : HomeSideEffect()

    data object NavigateToMap : HomeSideEffect()
    data class NavigateToDetail(val houseId: Long) : HomeSideEffect()
    data class NavigateToWebView(
        val webViewUrl: String
    ) : HomeSideEffect()
}