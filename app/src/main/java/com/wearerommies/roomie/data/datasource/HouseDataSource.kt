package com.wearerommies.roomie.data.datasource

import com.wearerommies.roomie.data.dto.response.BaseResponse
import com.wearerommies.roomie.data.dto.response.ResponseBookmarkListDto
import com.wearerommies.roomie.data.dto.response.ResponseDetailDto
import com.wearerommies.roomie.data.dto.response.ResponseMoodDto
import com.wearerommies.roomie.data.service.HouseService
import javax.inject.Inject

class HouseDataSource @Inject constructor(
    private val houseService: HouseService
) {
    suspend fun getMoodLists(moodTag: String): BaseResponse<ResponseMoodDto> =
        houseService.getMoodLists(moodTag = moodTag)

    suspend fun getBookmarkLists(): BaseResponse<ResponseBookmarkListDto> =
        houseService.getBookmarkLists()

    suspend fun bookmarkHouse(houseId: Long): BaseResponse<Boolean> =
        houseService.bookmarkHouse(houseId = houseId)

    suspend fun getHouseDetail(houseId: Long): BaseResponse<ResponseDetailDto> =
        houseService.getHouseDetail(houseId = houseId)
}
