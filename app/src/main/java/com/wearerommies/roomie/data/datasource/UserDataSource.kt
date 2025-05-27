package com.wearerommies.roomie.data.datasource

import com.wearerommies.roomie.data.dto.response.BaseResponse
import com.wearerommies.roomie.data.dto.response.ResponseHomeDto
import com.wearerommies.roomie.data.dto.response.ResponseMyPageDto
import com.wearerommies.roomie.data.service.UserService
import javax.inject.Inject

internal class UserDataSource @Inject constructor(
    private val userService: UserService
) {
    suspend fun getHomeData(): BaseResponse<ResponseHomeDto> =
        userService.getHomeData()

    suspend fun getUserInformation(): BaseResponse<ResponseMyPageDto> =
        userService.getUserInformation()


}