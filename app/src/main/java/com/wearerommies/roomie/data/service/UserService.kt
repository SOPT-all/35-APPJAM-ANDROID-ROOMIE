package com.wearerommies.roomie.data.service

import com.wearerommies.roomie.data.dto.request.RequestBirthDto
import com.wearerommies.roomie.data.dto.request.RequestContactDto
import com.wearerommies.roomie.data.dto.request.RequestGenderDto
import com.wearerommies.roomie.data.dto.request.RequestLocationDto
import com.wearerommies.roomie.data.dto.request.RequestNameDto
import com.wearerommies.roomie.data.dto.request.RequestNicknameDto
import com.wearerommies.roomie.data.dto.response.BaseResponse
import com.wearerommies.roomie.data.dto.response.ResponseAccountDto
import com.wearerommies.roomie.data.dto.response.ResponseBirthDto
import com.wearerommies.roomie.data.dto.response.ResponseContactDto
import com.wearerommies.roomie.data.dto.response.ResponseGenderDto
import com.wearerommies.roomie.data.dto.response.ResponseHomeDto
import com.wearerommies.roomie.data.dto.response.ResponseLocationDto
import com.wearerommies.roomie.data.dto.response.ResponseMyPageDto
import com.wearerommies.roomie.data.dto.response.ResponseNameDto
import com.wearerommies.roomie.data.dto.response.ResponseNicknameDto
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

    @PATCH("/v1/users/nickname")
    suspend fun editUserNickname(
        @Body request: RequestNicknameDto
    ): BaseResponse<ResponseNicknameDto>

    @PATCH("/v1/users/birthday")
    suspend fun editUserBirth(
        @Body request: RequestBirthDto
    ): BaseResponse<ResponseBirthDto>

    @PATCH("/v1/users/phonenumber")
    suspend fun editUserContact(
        @Body request: RequestContactDto
    ): BaseResponse<ResponseContactDto>

    @PATCH("/v1/users/gender")
    suspend fun editUserGender(
        @Body request: RequestGenderDto
    ): BaseResponse<ResponseGenderDto>

    @PATCH("/v1/users/location")
    suspend fun editUserLocation(
        @Body request: RequestLocationDto
    ): BaseResponse<ResponseLocationDto>

}