package com.wearerommies.roomie.data.repositoryimpl

import com.wearerommies.roomie.data.datasource.LoginDataSource
import com.wearerommies.roomie.data.dto.request.toDto
import com.wearerommies.roomie.domain.entity.SocialLoginEntity
import com.wearerommies.roomie.domain.entity.TokenEntity
import com.wearerommies.roomie.domain.repository.LoginRepository
import javax.inject.Inject

internal class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) : LoginRepository {
    override suspend fun postSocialLogin(loginData: SocialLoginEntity): Result<TokenEntity> =
        runCatching {
            loginDataSource.postSocialLogin(
                request = loginData.toDto()
            ).data.toEntity()
        }

    override suspend fun postTokenReissue(refreshToken: String): Result<TokenEntity> =
        runCatching {
            loginDataSource.postTokenReissue(
                refreshToken = refreshToken
            ).data.toEntity()
        }
}