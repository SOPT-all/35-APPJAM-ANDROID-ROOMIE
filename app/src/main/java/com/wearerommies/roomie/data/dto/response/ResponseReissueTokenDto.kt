package com.wearerommies.roomie.data.dto.response

import com.wearerommies.roomie.domain.entity.ReissueTokenEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseReissueTokenDto(
    @SerialName("accessToken")
    val accessToken: String
) {
    fun toEntity() = ReissueTokenEntity(
        accessToken = accessToken
    )
}