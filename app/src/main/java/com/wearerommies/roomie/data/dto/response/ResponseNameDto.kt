package com.wearerommies.roomie.data.dto.response

import com.wearerommies.roomie.domain.entity.NameEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseNameDto(
    @SerialName("name")
    val name: String
) {
    fun toEntity() = NameEntity(
        name = name
    )
}