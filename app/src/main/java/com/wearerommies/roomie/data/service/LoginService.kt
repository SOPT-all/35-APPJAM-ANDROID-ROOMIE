package com.wearerommies.roomie.data.service

import com.wearerommies.roomie.data.dto.request.RequestSocialLoginDto
import com.wearerommies.roomie.data.dto.response.BaseResponse
import com.wearerommies.roomie.data.dto.response.ResponseReissueTokenDto
import com.wearerommies.roomie.data.dto.response.ResponseTokenDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {
    @POST("/v1/auth/oauth/login")
    suspend fun postSocialLogin(
        @Body request: RequestSocialLoginDto
    ): BaseResponse<ResponseTokenDto>

    @POST("/v1/auth/oauth/reissue")
    suspend fun postTokenReissue(
        @Header("Authorization") refreshToken: String
    ): BaseResponse<ResponseReissueTokenDto>
}