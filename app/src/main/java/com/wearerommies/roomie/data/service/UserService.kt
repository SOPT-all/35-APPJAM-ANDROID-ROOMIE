package com.wearerommies.roomie.data.service

import com.wearerommies.roomie.data.dto.request.RequestNameDto
import com.wearerommies.roomie.data.dto.response.BaseResponse
import com.wearerommies.roomie.data.dto.response.ResponseAccountDto
import com.wearerommies.roomie.data.dto.response.ResponseHomeDto
import com.wearerommies.roomie.data.dto.response.ResponseMyPageDto
import com.wearerommies.roomie.data.dto.response.ResponseNameDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface UserService {
    @GET("/v1/users/home")
    suspend fun getHomeData(): BaseResponse<ResponseHomeDto>

    @GET("/v1/users/mypage")
    suspend fun getUserInformation(): BaseResponse<ResponseMyPageDto>

    @GET("/v1/users/mypage/accountinfo")
    suspend fun getUserAccountInformation(): BaseResponse<ResponseAccountDto>

    @PATCH("/v1/users/name")
    suspend fun editUserName(
        @Body request: RequestNameDto
    ): BaseResponse<ResponseNameDto>
}