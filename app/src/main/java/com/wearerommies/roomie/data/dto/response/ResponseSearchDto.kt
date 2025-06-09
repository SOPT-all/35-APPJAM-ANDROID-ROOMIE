package com.wearerommies.roomie.data.dto.response

import com.wearerommies.roomie.domain.entity.SearchResultEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSearchDto(
    @SerialName("locations")
    val locations: List<Location>
) {
    @Serializable
    data class Location(
        @SerialName("longitude")
        val longitude: Float,
        @SerialName("latitude")
        val latitude: Float,
        @SerialName("location")
        val location: String,
        @SerialName("address")
        val address: String,
        @SerialName("roadAddress")
        val roadAddress: String
    ) {
        fun toEntity() = SearchResultEntity(
            longitude = this.longitude,
            latitude = this.latitude,
            location = this.location,
            address = this.address,
            roadAddress = this.roadAddress
        )
    }

    fun toEntity() = locations.map { it.toEntity() }
}

