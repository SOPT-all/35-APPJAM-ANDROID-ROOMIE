package com.wearerommies.roomie.data.dto.request

import com.wearerommies.roomie.domain.entity.GenderEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestGenderDto(
    @SerialName("gender")
    val gender: String
)

fun GenderEntity.toDto() = RequestGenderDto(
    gender = gender
)