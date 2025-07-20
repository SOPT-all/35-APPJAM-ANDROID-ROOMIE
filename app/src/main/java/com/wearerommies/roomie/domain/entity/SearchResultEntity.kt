package com.wearerommies.roomie.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class SearchResultEntity(
    val longitude: Float = -1F,
    val latitude: Float = -1F,
    val location: String = "",
    val address: String = "",
    val roadAddress: String = ""
)
