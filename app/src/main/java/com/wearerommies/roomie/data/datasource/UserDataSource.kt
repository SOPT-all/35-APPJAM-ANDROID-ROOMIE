package com.wearerommies.roomie.data.datasource

import com.wearerommies.roomie.data.dto.request.RequestSocialLoginDto
import com.wearerommies.roomie.data.dto.request.RequestSocialSignUpDto
import com.wearerommies.roomie.data.dto.response.BaseResponse
import com.wearerommies.roomie.data.dto.response.ResponseHomeDto
import com.wearerommies.roomie.data.dto.response.ResponseMyPageDto
import com.wearerommies.roomie.data.dto.response.ResponseTokenDto
import com.wearerommies.roomie.data.service.UserService
import javax.inject.Inject

internal class UserDataSource @Inject constructor(
    private val userService: UserService
) {
    suspend fun getHomeData(accessToken: String): BaseResponse<ResponseHomeDto> =
        userService.getHomeData(accessToken = "Bearer $accessToken")

    suspend fun getUserInformation(): BaseResponse<ResponseMyPageDto> =
        userService.getUserInformation()

    suspend fun postSocialLogin(request: RequestSocialLoginDto): BaseResponse<ResponseTokenDto> =
        userService.postSocialLogin(request = request)

    suspend fun postSocialSignUp(request: RequestSocialSignUpDto): BaseResponse<ResponseTokenDto> =
        userService.postSocialSignUp(request = request)

    suspend fun postTokenReissue(refreshToken: String): BaseResponse<ResponseTokenDto> =
        userService.postTokenReissue(refreshToken = refreshToken)
}