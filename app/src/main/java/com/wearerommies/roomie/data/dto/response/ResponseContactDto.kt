package com.wearerommies.roomie.data.dto.response

import com.wearerommies.roomie.domain.entity.ContactEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseContactDto(
    @SerialName("phoneNumber")
    val phoneNumber: String
) {
    fun toEntity() = ContactEntity(
        phoneNumber = phoneNumber
    )
}