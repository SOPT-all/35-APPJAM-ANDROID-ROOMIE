package com.wearerommies.roomie.data.di

import com.wearerommies.roomie.data.repositoryimpl.HouseRepositoryImpl
import com.wearerommies.roomie.data.repositoryimpl.MapRepositoryImpl
import com.wearerommies.roomie.data.repositoryimpl.RoomRepositoryImpl
import com.wearerommies.roomie.data.repositoryimpl.UserRepositoryImpl
import com.wearerommies.roomie.domain.repository.HouseRepository
import com.wearerommies.roomie.domain.repository.MapRepository
import com.wearerommies.roomie.domain.repository.RoomRepository
import com.wearerommies.roomie.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @Binds
    @Singleton
    fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindsHouseRepository(houseRepositoryImpl: HouseRepositoryImpl): HouseRepository

    @Binds
    @Singleton
    fun bindsMapRepository(mapRepositoryImpl: MapRepositoryImpl): MapRepository

    @Binds
    @Singleton
    fun bindsRoomRepository(roomRepositoryImpl: RoomRepositoryImpl): RoomRepository
}
