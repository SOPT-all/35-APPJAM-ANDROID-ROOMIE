package com.wearerommies.roomie.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class SearchResultEntity(
    val longitude: Float? = null,
    val latitude: Float? = null,
    val location: String = "",
    val address: String = "",
    val roadAddress: String = ""
)
