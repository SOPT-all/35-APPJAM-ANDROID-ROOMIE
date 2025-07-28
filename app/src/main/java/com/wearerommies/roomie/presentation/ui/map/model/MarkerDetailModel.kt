package com.wearerommies.roomie.presentation.ui.map.model

import com.wearerommies.roomie.domain.entity.FilterResultEntity

data class MarkerDetailModel(
    val houseId: Long,
    val monthlyRent: String,
    val deposit: String,
    val contractTerm: Int,
    val gender: String,
    val occupancy: String,
    val location: String,
    val locationDescription: String,
    val moodTag: String,
)

fun FilterResultEntity.HouseEntity.toMarkerDetailModel() = MarkerDetailModel(
    houseId = houseId,
    monthlyRent = monthlyRent,
    deposit = deposit,
    contractTerm = contractTerm,
    gender = genderPolicy,
    occupancy = occupancyTypes,
    location = location,
    locationDescription = locationDescription,
    moodTag = moodTag
)
