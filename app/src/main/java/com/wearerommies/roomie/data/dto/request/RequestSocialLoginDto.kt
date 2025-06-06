package com.wearerommies.roomie.data.dto.request

import com.wearerommies.roomie.domain.entity.SocialLoginEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestSocialLoginDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("provider")
    val provider: String
)

fun SocialLoginEntity.toDto() = RequestSocialLoginDto(
    accessToken = accessToken,
    provider = provider
)