package com.wearerommies.roomie.domain.repository

import com.wearerommies.roomie.domain.entity.AccountEntity
import com.wearerommies.roomie.domain.entity.HomeDataEntity
import com.wearerommies.roomie.domain.entity.MyPageEntity
import com.wearerommies.roomie.domain.entity.NameEntity

interface UserRepository {
    suspend fun getHomeData(): Result<HomeDataEntity>
    suspend fun getUserInformation(): Result<MyPageEntity>
    suspend fun getUserAccountInformation(): Result<AccountEntity>
    suspend fun editUserName(name: NameEntity): Result<NameEntity>
}