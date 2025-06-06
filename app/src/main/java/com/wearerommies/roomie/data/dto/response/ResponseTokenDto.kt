package com.wearerommies.roomie.data.dto.response


import com.wearerommies.roomie.domain.entity.TokenEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseTokenDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String
) {
    fun toEntity() = TokenEntity(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}