package com.wearerommies.roomie.data.dto.request

import com.wearerommies.roomie.domain.entity.NicknameEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestNicknameDto(
    @SerialName("nickname")
    val nickname: String
)

fun NicknameEntity.toDto() = RequestNicknameDto(
    nickname = nickname
)