package com.wearerommies.roomie.domain.repository

import com.wearerommies.roomie.domain.entity.ReissueTokenEntity
import com.wearerommies.roomie.domain.entity.SocialLoginEntity
import com.wearerommies.roomie.domain.entity.TokenEntity

interface LoginRepository {
    suspend fun postSocialLogin(loginData: SocialLoginEntity): Result<TokenEntity>
    suspend fun postTokenReissue(refreshToken: String): Result<ReissueTokenEntity>
}