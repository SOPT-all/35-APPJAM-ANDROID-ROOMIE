package com.wearerommies.roomie.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.wearerommies.roomie.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val CONTENT_TYPE = "application/json"
    private const val BASE_URL = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
        }
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Timber.tag("Retrofit2").d("CONNECTION INFO -> $message")
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    @JWT
    fun provideOauthInterceptor(authInterceptor: OauthInterceptor): Interceptor = authInterceptor

    @Provides
    @Singleton
    @JWT
    fun provideJWTOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @JWT oauthInterceptor: Interceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) builder
            .addInterceptor(loggingInterceptor)
            .addInterceptor(oauthInterceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    @NoToken
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    @JWT
    fun provideRetrofit(@JWT okHttpClient: OkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(CONTENT_TYPE.toMediaType()))
            .build()
    }

    @Singleton
    @Provides
    @NoToken
    fun provideReissueRetrofit(@NoToken okHttpClient: OkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(CONTENT_TYPE.toMediaType()))
            .build()
    }
}
