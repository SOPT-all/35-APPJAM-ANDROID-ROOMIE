package com.wearerommies.roomie.data.service

import com.wearerommies.roomie.data.dto.request.RequestSocialLoginDto
import com.wearerommies.roomie.data.dto.request.RequestSocialSignUpDto
import com.wearerommies.roomie.data.dto.response.BaseResponse
import com.wearerommies.roomie.data.dto.response.ResponseHomeDto
import com.wearerommies.roomie.data.dto.response.ResponseMyPageDto
import com.wearerommies.roomie.data.dto.response.ResponseTokenDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @GET("/v1/users/home")
    suspend fun getHomeData(
        @Header("Authorization") accessToken: String
    ): BaseResponse<ResponseHomeDto>

    @GET("/v1/users/mypage")
    suspend fun getUserInformation(): BaseResponse<ResponseMyPageDto>

    @POST("/v1/auth/oauth/login")
    suspend fun postSocialLogin(
        @Body request: RequestSocialLoginDto
    ): BaseResponse<ResponseTokenDto>

    @POST("/v1/auth/oauth/signup")
    suspend fun postSocialSignUp(
        @Body request: RequestSocialSignUpDto
    ): BaseResponse<ResponseTokenDto>

    @POST("/v1/auth/oauth/reissue")
    suspend fun postTokenReissue(
        @Header("Authorization") refreshToken: String
    ): BaseResponse<ResponseTokenDto>
}