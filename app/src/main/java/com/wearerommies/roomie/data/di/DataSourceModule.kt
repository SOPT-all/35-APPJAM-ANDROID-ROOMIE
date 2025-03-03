package com.wearerommies.roomie.data.di

import com.wearerommies.roomie.data.datasource.UserDataSource
import com.wearerommies.roomie.data.datasource.HouseDataSource
import com.wearerommies.roomie.data.datasource.MapDataSource
import com.wearerommies.roomie.data.datasource.RoomDataSource
import com.wearerommies.roomie.data.service.UserService
import com.wearerommies.roomie.data.service.HouseService
import com.wearerommies.roomie.data.service.MapService
import com.wearerommies.roomie.data.service.RoomService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataSourceModule {
    @Provides
    @Singleton
    fun providesUserDataSource(
        userService: UserService
    ): UserDataSource = UserDataSource(userService)

    @Provides
    @Singleton
    fun providesHouseDataSource(
        houseService: HouseService
    ): HouseDataSource = HouseDataSource(houseService)

    @Provides
    @Singleton
    fun providesMapDataSource(
        mapService: MapService
    ): MapDataSource = MapDataSource(mapService)

    @Provides
    @Singleton
    fun providesRoomDataSource(
        roomService: RoomService
    ): RoomDataSource = RoomDataSource(roomService)
}
