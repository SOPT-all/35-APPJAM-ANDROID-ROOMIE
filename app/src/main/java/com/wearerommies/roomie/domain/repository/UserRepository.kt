package com.wearerommies.roomie.domain.repository

import com.wearerommies.roomie.domain.entity.AccountEntity
import com.wearerommies.roomie.domain.entity.HomeDataEntity
import com.wearerommies.roomie.domain.entity.MyPageEntity

interface UserRepository {
    suspend fun getHomeData(): Result<HomeDataEntity>
    suspend fun getUserInformation(): Result<MyPageEntity>
    suspend fun getUserAccountInformation(): Result<AccountEntity>
}