package com.wearerommies.roomie.data.dto.request

import com.wearerommies.roomie.domain.entity.NameEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestNameDto(
    @SerialName("name")
    val name: String
)

fun NameEntity.toDto() = RequestNameDto(
    name = name
)