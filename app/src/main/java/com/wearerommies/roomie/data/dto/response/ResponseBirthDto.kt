package com.wearerommies.roomie.data.dto.response

import com.wearerommies.roomie.domain.entity.BirthEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseBirthDto(
    @SerialName("birthDay")
    val birthDay: String
) {
    fun toEntity() = BirthEntity(
        birthDay = birthDay
    )
}