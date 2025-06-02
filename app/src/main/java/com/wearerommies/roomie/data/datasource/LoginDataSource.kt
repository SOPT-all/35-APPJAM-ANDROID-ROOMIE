package com.wearerommies.roomie.data.datasource

import com.wearerommies.roomie.data.dto.request.RequestSocialLoginDto
import com.wearerommies.roomie.data.dto.response.BaseResponse
import com.wearerommies.roomie.data.dto.response.ResponseReissueTokenDto
import com.wearerommies.roomie.data.dto.response.ResponseTokenDto
import com.wearerommies.roomie.data.service.LoginService
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val loginService: LoginService
) {
    suspend fun postSocialLogin(request: RequestSocialLoginDto): BaseResponse<ResponseTokenDto> =
        loginService.postSocialLogin(request = request)

    suspend fun postTokenReissue(refreshToken: String): BaseResponse<ResponseReissueTokenDto> =
        loginService.postTokenReissue(refreshToken = refreshToken)
}