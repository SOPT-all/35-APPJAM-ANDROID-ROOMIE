package com.wearerommies.roomie.data.di

import com.wearerommies.roomie.data.service.UserService
import com.wearerommies.roomie.data.service.HouseService
import com.wearerommies.roomie.data.service.MapService
import com.wearerommies.roomie.data.service.RoomService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {
    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideHouseService(retrofit: Retrofit): HouseService =
        retrofit.create(HouseService::class.java)

    @Provides
    @Singleton
    fun provideMapService(retrofit: Retrofit): MapService =
        retrofit.create(MapService::class.java)

    @Provides
    @Singleton
    fun provideRoomService(retrofit: Retrofit): RoomService =
        retrofit.create(RoomService::class.java)
}
