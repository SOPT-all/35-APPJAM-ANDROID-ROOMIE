package com.wearerommies.roomie.data.repositoryimpl

import com.wearerommies.roomie.data.datasource.UserDataSource
import com.wearerommies.roomie.data.dto.request.toDto
import com.wearerommies.roomie.domain.entity.HomeDataEntity
import com.wearerommies.roomie.domain.entity.MyPageEntity
import com.wearerommies.roomie.domain.entity.SocialLoginEntity
import com.wearerommies.roomie.domain.entity.SocialSignUpEntity
import com.wearerommies.roomie.domain.entity.TokenEntity
import com.wearerommies.roomie.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getHomeData(): Result<HomeDataEntity> =
        runCatching {
            userDataSource.getHomeData().data.toEntity()
        }

    override suspend fun getUserInformation(): Result<MyPageEntity> =
        runCatching {
            userDataSource.getUserInformation().data.toEntity()
        }

    override suspend fun postSocialLogin(loginData: SocialLoginEntity): Result<TokenEntity> =
        runCatching {
            userDataSource.postSocialLogin(
                request = loginData.toDto()
            ).data.toEntity()
        }

    override suspend fun postSocialSignUp(signupData: SocialSignUpEntity): Result<TokenEntity> =
        runCatching {
            userDataSource.postSocialSignUp(
                request = signupData.toDto()
            ).data.toEntity()
        }

    override suspend fun postTokenReissue(refreshToken: String): Result<TokenEntity> =
        runCatching {
            userDataSource.postTokenReissue(
                refreshToken = refreshToken
            ).data.toEntity()
        }
}