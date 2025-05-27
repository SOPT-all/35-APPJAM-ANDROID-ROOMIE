package com.wearerommies.roomie.data.repositoryimpl

import com.wearerommies.roomie.data.datasource.UserDataSource
import com.wearerommies.roomie.domain.entity.HomeDataEntity
import com.wearerommies.roomie.domain.entity.MyPageEntity
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
}