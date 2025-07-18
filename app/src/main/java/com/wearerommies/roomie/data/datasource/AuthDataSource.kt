package com.wearerommies.roomie.data.datasource

import com.wearerommies.roomie.data.dto.request.RequestSocialLoginDto
import com.wearerommies.roomie.data.dto.response.BaseResponse
import com.wearerommies.roomie.data.dto.response.ResponseReissueTokenDto
import com.wearerommies.roomie.data.dto.response.ResponseTokenDto
import com.wearerommies.roomie.data.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun postSocialLogin(request: RequestSocialLoginDto): BaseResponse<ResponseTokenDto> =
        authService.postSocialLogin(request = request)

    suspend fun postTokenReissue(refreshToken: String): BaseResponse<ResponseReissueTokenDto> =
        authService.postTokenReissue(refreshToken = refreshToken)

    suspend fun deleteLogout(refreshToken: String) =
        authService.deleteLogout(refreshToken = refreshToken)

    suspend fun deleteWithdraw(refreshToken: String) =
        authService.deleteWithdraw(refreshToken = refreshToken)
}