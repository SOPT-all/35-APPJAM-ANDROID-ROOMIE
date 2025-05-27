package com.wearerommies.roomie.data.service

import com.wearerommies.roomie.data.dto.response.BaseResponse
import com.wearerommies.roomie.data.dto.response.ResponseHomeDto
import com.wearerommies.roomie.data.dto.response.ResponseMyPageDto
import retrofit2.http.GET

interface UserService {
    @GET("/v1/users/home")
    suspend fun getHomeData(): BaseResponse<ResponseHomeDto>

    @GET("/v1/users/mypage")
    suspend fun getUserInformation(): BaseResponse<ResponseMyPageDto>
}