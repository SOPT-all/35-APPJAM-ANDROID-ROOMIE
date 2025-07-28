package com.wearerommies.roomie.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class FilterEntity(
    val location: String? = null,
    val moodTag: List<String> = listOf(),
    val depositRange: DepositRange = DepositRange(),
    val monthlyRentRange: MonthlyRentRange = MonthlyRentRange(),
    val genderPolicy: List<String> = listOf(),
    val preferredDate: String? = null,
    val occupancyTypes: List<String> = listOf(),
    val contractPeriod: List<Int> = listOf(),
    val latitude: Float? = null,
    val longitude: Float? = null
) {
    @Serializable
    data class DepositRange(
        val min: Int = 0,
        val max: Int = 500
    )

    @Serializable
    data class MonthlyRentRange(
        val min: Int = 0,
        val max: Int = 150
    )
}
