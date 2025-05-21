package com.wearerommies.roomie.data.dto.request

import com.wearerommies.roomie.domain.entity.SocialSignUpEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestSocialSignUpDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("provider")
    val provider: String
)

fun SocialSignUpEntity.toDto() = RequestSocialSignUpDto(
    accessToken = accessToken,
    nickname = nickname,
    provider = provider
)