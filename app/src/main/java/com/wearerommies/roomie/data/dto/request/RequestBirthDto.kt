package com.wearerommies.roomie.data.dto.request

import com.wearerommies.roomie.domain.entity.BirthEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestBirthDto(
    @SerialName("birthDay")
    val birthDay: String
)

fun BirthEntity.toDto() = RequestBirthDto(
    birthDay = birthDay
)