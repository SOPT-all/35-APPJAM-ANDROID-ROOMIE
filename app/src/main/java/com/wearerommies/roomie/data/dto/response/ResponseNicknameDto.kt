package com.wearerommies.roomie.data.dto.response

import com.wearerommies.roomie.domain.entity.NicknameEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseNicknameDto(
    @SerialName("nickname")
    val nickname: String
) {
    fun toEntity() = NicknameEntity(
        nickname = nickname
    )
}