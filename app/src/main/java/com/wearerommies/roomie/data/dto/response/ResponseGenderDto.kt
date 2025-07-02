package com.wearerommies.roomie.data.dto.response

import com.wearerommies.roomie.domain.entity.GenderEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGenderDto(
    @SerialName("gender")
    val gender: String
) {
    fun toEntity() = GenderEntity(
        gender = gender
    )
}