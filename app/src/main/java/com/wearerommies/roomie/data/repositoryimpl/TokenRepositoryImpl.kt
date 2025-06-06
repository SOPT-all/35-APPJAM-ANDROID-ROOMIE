package com.wearerommies.roomie.data.repositoryimpl

import com.wearerommies.roomie.data.datalocal.datasource.TokenDataSource
import com.wearerommies.roomie.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenDataSource: TokenDataSource,
): TokenRepository {
    override fun getAccessToken(): String = tokenDataSource.accessToken
    override fun getRefreshToken(): String = tokenDataSource.refreshToken

    override fun setTokens(accessToken:String, refreshToken: String) {
        tokenDataSource.accessToken = accessToken
        tokenDataSource.refreshToken = refreshToken
    }

    override fun clearInfo() {
        tokenDataSource.clearInfo()
    }
}