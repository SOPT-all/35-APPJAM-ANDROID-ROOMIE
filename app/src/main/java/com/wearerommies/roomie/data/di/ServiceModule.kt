package com.wearerommies.roomie.data.di

import com.wearerommies.roomie.data.di.qualifier.JWT
import com.wearerommies.roomie.data.di.qualifier.NoToken
import com.wearerommies.roomie.data.service.HouseService
import com.wearerommies.roomie.data.service.AuthService
import com.wearerommies.roomie.data.service.MapService
import com.wearerommies.roomie.data.service.RoomService
import com.wearerommies.roomie.data.service.UserService
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
    fun provideUserService(@JWT retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(@NoToken retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideHouseService(@JWT retrofit: Retrofit): HouseService =
        retrofit.create(HouseService::class.java)

    @Provides
    @Singleton
    fun provideMapService(@JWT retrofit: Retrofit): MapService =
        retrofit.create(MapService::class.java)

    @Provides
    @Singleton
    fun provideRoomService(@JWT retrofit: Retrofit): RoomService =
        retrofit.create(RoomService::class.java)
}
