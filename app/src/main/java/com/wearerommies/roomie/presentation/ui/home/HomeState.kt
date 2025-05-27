package com.wearerommies.roomie.presentation.ui.home

import com.wearerommies.roomie.domain.entity.HomeDataEntity
import com.wearerommies.roomie.domain.entity.RoomCardEntity

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
    )
)