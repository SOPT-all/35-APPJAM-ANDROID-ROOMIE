package com.wearerommies.roomie.presentation.ui.home

import com.wearerommies.roomie.domain.entity.HomeDataEntity
import com.wearerommies.roomie.domain.entity.RoomCardEntity
import com.wearerommies.roomie.domain.entity.SearchResultEntity
import com.wearerommies.roomie.presentation.core.util.EmptyUiState
import kotlinx.collections.immutable.PersistentList

data class HomeState(
    val uiState: HomeDataEntity = HomeDataEntity(
        nickname = "",
        location = "",
        recentlyViewedHouses = listOf(
            RoomCardEntity(
                houseId = 1,
                monthlyRent = "",
                deposit = "",
                occupancyType = "",
                location = "",
                genderPolicy = "",
                locationDescription = "",
                isPinned = false,
                moodTag = "",
                contractTerm = 1,
                mainImgUrl = ""
            ),
        )
    ),
    val isShowBottomSheet: Boolean = false,
    val bottomSheetState: LocationBottomSheetState = LocationBottomSheetState()
)

data class LocationBottomSheetState(
    val searchKeyword: String = "",
    val searchResults: EmptyUiState<PersistentList<SearchResultEntity>> = EmptyUiState.Initial
)

