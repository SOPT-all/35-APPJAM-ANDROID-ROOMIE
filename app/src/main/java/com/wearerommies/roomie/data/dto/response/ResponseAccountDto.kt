package com.wearerommies.roomie.data.dto.response

import com.wearerommies.roomie.domain.entity.AccountEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseAccountDto(
    @SerialName("birthDate")
    val birthDate: String?,
    @SerialName("gender")
    val gender: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("nickname")
    val nickname: String?,
    @SerialName("phoneNumber")
    val phoneNumber: String?,
    @SerialName("socialType")
    val socialType: String?
) {
    fun toEntity() = AccountEntity(
        birthDate = birthDate,
        gender = gender,
        name = name,
        nickname = nickname,
        phoneNumber = phoneNumber,
        socialType = socialType
    )
}