package com.wearerommies.roomie.presentation.ui.map

import androidx.annotation.StringRes
import com.wearerommies.roomie.domain.entity.FilterEntity
import com.wearerommies.roomie.domain.entity.SearchResultEntity

sealed class MapSideEffect {
    data class SnackBar(@StringRes val message: Int) : MapSideEffect()
    data class NavigateToDetail(val houseId: Long) : MapSideEffect()
    data object NavigateToSearch: MapSideEffect()
    data class NavigateToFilter(val filter: FilterEntity, val searchResult: SearchResultEntity,): MapSideEffect()
}
