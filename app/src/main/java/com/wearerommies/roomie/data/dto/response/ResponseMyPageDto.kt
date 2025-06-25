package com.wearerommies.roomie.data.dto.response

import com.wearerommies.roomie.domain.entity.MyPageEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMyPageDto(
    @SerialName("nickname")
    val nickname: String,
    @SerialName("socialType")
    val socialType: String
) {
    fun toEntity() = MyPageEntity(
        nickname = nickname,
        socialType = socialType
    )
}