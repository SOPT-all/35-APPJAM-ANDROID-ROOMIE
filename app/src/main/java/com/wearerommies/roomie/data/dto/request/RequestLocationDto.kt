package com.wearerommies.roomie.data.dto.request

import com.wearerommies.roomie.domain.entity.LocationEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestLocationDto(
    @SerialName("latitude")
    val latitude: Float,
    @SerialName("longitude")
    val longitude: Float,
    @SerialName("location")
    val location: String
)

fun LocationEntity.toDto() = RequestLocationDto(
    latitude = latitude,
    longitude = longitude,
    location = location
)