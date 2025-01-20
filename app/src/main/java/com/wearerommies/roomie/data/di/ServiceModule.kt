package com.wearerommies.roomie.data.di

import com.wearerommies.roomie.data.service.BookmarkListService
import com.wearerommies.roomie.data.service.HomeService
import com.wearerommies.roomie.data.service.MoodService
import com.wearerommies.roomie.data.service.ReqresService
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
    fun provideReqresService(retrofit: Retrofit): ReqresService =
        retrofit.create(ReqresService::class.java)

    @Provides
    @Singleton
    fun provideHomeService(retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @Provides
    @Singleton
    fun provideMoodService(retrofit: Retrofit): MoodService =
        retrofit.create(MoodService::class.java)

    @Provides
    @Singleton
    fun provideBookmarkListService(retrofit: Retrofit): BookmarkListService =
        retrofit.create(BookmarkListService::class.java)
}
