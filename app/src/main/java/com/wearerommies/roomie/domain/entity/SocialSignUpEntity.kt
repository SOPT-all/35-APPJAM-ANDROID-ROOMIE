package com.wearerommies.roomie.domain.entity

data class SocialSignUpEntity(
    val accessToken: String,
    val nickname: String,
    val provider: String
)
