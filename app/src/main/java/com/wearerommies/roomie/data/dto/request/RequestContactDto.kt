package com.wearerommies.roomie.data.dto.request

import com.wearerommies.roomie.domain.entity.ContactEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestContactDto(
    @SerialName("phoneNumber")
    val phoneNumber: String
)

fun ContactEntity.toDto() = RequestContactDto(
    phoneNumber = phoneNumber
)