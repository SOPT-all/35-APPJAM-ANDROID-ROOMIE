package com.wearerommies.roomie.data.datasource

import com.wearerommies.roomie.data.dto.request.RequestNameDto
import com.wearerommies.roomie.data.dto.response.BaseResponse
import com.wearerommies.roomie.data.dto.response.ResponseAccountDto
import com.wearerommies.roomie.data.dto.response.ResponseHomeDto
import com.wearerommies.roomie.data.dto.response.ResponseMyPageDto
import com.wearerommies.roomie.data.dto.response.ResponseNameDto
import com.wearerommies.roomie.data.service.UserService
import javax.inject.Inject

internal class UserDataSource @Inject constructor(
    private val userService: UserService
) {
    suspend fun getHomeData(): BaseResponse<ResponseHomeDto> =
        userService.getHomeData()

    suspend fun getUserInformation(): BaseResponse<ResponseMyPageDto> =
        userService.getUserInformation()

    suspend fun getUserAccountInformation(): BaseResponse<ResponseAccountDto> =
        userService.getUserAccountInformation()

    suspend fun editUserName(request: RequestNameDto): BaseResponse<ResponseNameDto> =
        userService.editUserName(request = request)
}