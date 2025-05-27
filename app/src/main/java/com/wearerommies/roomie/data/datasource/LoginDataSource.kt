package com.wearerommies.roomie.data.datasource

import com.wearerommies.roomie.data.dto.request.RequestSocialLoginDto
import com.wearerommies.roomie.data.dto.response.BaseResponse
import com.wearerommies.roomie.data.dto.response.ResponseTokenDto
import com.wearerommies.roomie.data.service.LoginService
import javax.inject.Inject

internal class LoginDataSource @Inject constructor(
    private val loginService: LoginService
) {
    suspend fun postSocialLogin(request: RequestSocialLoginDto): BaseResponse<ResponseTokenDto> =
        loginService.postSocialLogin(request = request)

    suspend fun postTokenReissue(refreshToken: String): BaseResponse<ResponseTokenDto> =
        loginService.postTokenReissue(refreshToken = refreshToken)
}