package com.wearerommies.roomie.presentation.ui.map

import com.wearerommies.roomie.domain.entity.FilterEntity
import com.wearerommies.roomie.domain.entity.FilterResultEntity
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MapState(
    val longitude: Float? = null,
    val latitude: Float? = null,
    val isBottomSheetOpened: Boolean = true,
    val filter: FilterEntity = FilterEntity(),
    val isFullSelected: Boolean = false,
    val houseList: PersistentList<FilterResultEntity.HouseEntity> = persistentListOf(),
    val markerDetail: FilterResultEntity.HouseEntity = FilterResultEntity.HouseEntity(
        latitude = 0F,
        longitude = 0F,
        houseId = 0,
        monthlyRent = "",
        deposit = "",
        contractTerm = 0,
        genderPolicy = "",
        occupancyTypes = "",
        location = "",
        locationDescription = "",
        moodTag = "",
        isPinned = false,
        mainImgUrl = "",
        excludeFull = false
    ),
    val clickedMarkerId: Long? = null
)
