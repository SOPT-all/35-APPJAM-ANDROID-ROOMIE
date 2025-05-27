package com.wearerommies.roomie.domain.repository

import com.wearerommies.roomie.domain.entity.HomeDataEntity
import com.wearerommies.roomie.domain.entity.MyPageEntity
import com.wearerommies.roomie.domain.entity.SocialLoginEntity
import com.wearerommies.roomie.domain.entity.SocialSignUpEntity
import com.wearerommies.roomie.domain.entity.TokenEntity

interface UserRepository {
    suspend fun getHomeData() : Result<HomeDataEntity>
    suspend fun getUserInformation(): Result<MyPageEntity>
    suspend fun postSocialLogin(loginData: SocialLoginEntity): Result<TokenEntity>
    suspend fun postSocialSignUp(signupData: SocialSignUpEntity): Result<TokenEntity>
    suspend fun postTokenReissue(refreshToken: String): Result<TokenEntity>
}