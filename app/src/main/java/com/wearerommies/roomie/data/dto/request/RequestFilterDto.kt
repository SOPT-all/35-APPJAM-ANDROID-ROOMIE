package com.wearerommies.roomie.data.dto.request

import com.wearerommies.roomie.data.dto.request.RequestFilterDto.DepositRange
import com.wearerommies.roomie.data.dto.request.RequestFilterDto.MonthlyRentRange
import com.wearerommies.roomie.domain.entity.FilterEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestFilterDto(
    @SerialName("contractPeriod")
    val contractPeriod: List<Int>,
    @SerialName("depositRange")
    val depositRange: DepositRange,
    @SerialName("genderPolicy")
    val genderPolicy: List<String>,
    @SerialName("location")
    val location: String?,
    @SerialName("monthlyRentRange")
    val monthlyRentRange: MonthlyRentRange,
    @SerialName("moodTag")
    val moodTag: String?,
    @SerialName("occupancyTypes")
    val occupancyTypes: List<String>,
    @SerialName("preferredDate")
    val preferredDate: String?,
    @SerialName("latitude")
    val latitude: Float? = null,
    @SerialName("longitude")
    val longitude: Float? = null
) {
    @Serializable
    data class DepositRange(
        @SerialName("max")
        val max: Int,
        @SerialName("min")
        val min: Int
    )

    @Serializable
    data class MonthlyRentRange(
        @SerialName("max")
        val max: Int,
        @SerialName("min")
        val min: Int
    )
}

fun FilterEntity.toDto() = RequestFilterDto(
    contractPeriod = contractPeriod,
    depositRange = DepositRange(
        min = depositRange.min,
        max = depositRange.max
    ),
    genderPolicy = genderPolicy,
    location = location,
    monthlyRentRange = MonthlyRentRange(
        min = monthlyRentRange.min,
        max = monthlyRentRange.max
    ),
    moodTag = moodTag,
    occupancyTypes = occupancyTypes,
    preferredDate = preferredDate,
    latitude = latitude,
    longitude = longitude
)
