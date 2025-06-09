package com.wearerommies.roomie.data.dto.response

import com.wearerommies.roomie.domain.entity.FilterResultEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseFilterDto(
    @SerialName("houses")
    val houses: List<House>
) {
    @Serializable
    data class House(
        @SerialName("houseId")
        val houseId: Long,
        @SerialName("latitude")
        val latitude: Float,
        @SerialName("longitude")
        val longitude: Float,
        @SerialName("monthlyRent")
        val monthlyRent: String,
        @SerialName("deposit")
        val deposit: String,
        @SerialName("occupancyTypes")
        val occupancyTypes: String,
        @SerialName("location")
        val location: String,
        @SerialName("genderPolicy")
        val genderPolicy: String,
        @SerialName("locationDescription")
        val locationDescription: String,
        @SerialName("isPinned")
        val isPinned: Boolean,
        @SerialName("moodTag")
        val moodTag: String,
        @SerialName("contractTerm")
        val contractTerm: Int,
        @SerialName("mainImgUrl")
        val mainImgUrl: String,
        @SerialName("excludeFull")
        val excludeFull: Boolean
    ) {
        fun toEntity() = FilterResultEntity(
            houseId = houseId,
            latitude = latitude,
            longitude = longitude,
            monthlyRent = monthlyRent,
            deposit = deposit,
            occupancyTypes = occupancyTypes,
            location = location,
            genderPolicy = genderPolicy,
            locationDescription = locationDescription,
            isPinned = isPinned,
            moodTag = moodTag,
            contractTerm = contractTerm,
            mainImgUrl = mainImgUrl,
            excludeFull = excludeFull
        )
    }

    fun toEntity() = houses.map { it.toEntity() }
}
