package com.wearerommies.roomie.data.repositoryimpl

import com.wearerommies.roomie.data.datasource.AuthDataSource
import com.wearerommies.roomie.data.dto.request.toDto
import com.wearerommies.roomie.domain.entity.ReissueTokenEntity
import com.wearerommies.roomie.domain.entity.SocialLoginEntity
import com.wearerommies.roomie.domain.entity.TokenEntity
import com.wearerommies.roomie.domain.repository.AuthRepository
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun postSocialLogin(loginData: SocialLoginEntity): Result<TokenEntity> =
        runCatching {
            authDataSource.postSocialLogin(
                request = loginData.toDto()
            ).data.toEntity()
        }

    override suspend fun postTokenReissue(refreshToken: String): Result<ReissueTokenEntity> =
        runCatching {
            authDataSource.postTokenReissue(
                refreshToken = refreshToken
            ).data.toEntity()
        }

    override suspend fun deleteLogout(refreshToken: String): Result<Unit> =
        runCatching {
            authDataSource.deleteLogout(refreshToken = refreshToken)
        }

    override suspend fun deleteWithdraw(refreshToken: String): Result<Unit> =
        runCatching {
            authDataSource.deleteWithdraw(refreshToken = refreshToken)
        }
}