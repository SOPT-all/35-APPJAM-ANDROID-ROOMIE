package com.wearerommies.roomie.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class SearchResultEntity(
    val longitude: Float = 126.9377f,
    val latitude: Float = 37.55438f,
    val location: String = "",
    val address: String = "서울특별시 마포구 노고산동",
    val roadAddress: String = ""
)
