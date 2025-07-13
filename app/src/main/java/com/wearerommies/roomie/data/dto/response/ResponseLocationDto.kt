package com.wearerommies.roomie.data.dto.response

import com.wearerommies.roomie.domain.entity.LocationEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseLocationDto(
    @SerialName("latitude")
    val latitude: Float,
    @SerialName("longitude")
    val longitude: Float,
    @SerialName("location")
    val location: String
){
    fun toEntity() = LocationEntity(
        latitude = latitude,
        longitude = longitude,
        location = location
    )
}
