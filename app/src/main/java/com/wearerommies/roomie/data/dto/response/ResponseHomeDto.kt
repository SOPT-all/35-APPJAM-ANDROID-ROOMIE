package com.wearerommies.roomie.data.dto.response


import com.wearerommies.roomie.domain.entity.HomeDataEntity
import com.wearerommies.roomie.domain.entity.RoomCardEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseHomeDto(
    @SerialName("location")
    val location: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("recentlyViewedHouses")
    val recentlyViewedHouses: List<RecentlyViewedHouse>
) {
    @Serializable
    data class RecentlyViewedHouse(
        @SerialName("houseId")
        val houseId: Long,
        @SerialName("monthlyRent")
        val monthlyRent: String,
        @SerialName("deposit")
        val deposit: String,
        @SerialName("occupancyTypes")
        val occupancyTypes: String,
        @SerialName("location")
        val location: String,
        @SerialName("genderPolicy")
        val genderPolicy: String,
        @SerialName("locationDescription")
        val locationDescription: String,
        @SerialName("isPinned")
        val isPinned: Boolean,
        @SerialName("moodTag")
        val moodTag: String,
        @SerialName("contractTerm")
        val contractTerm: Int,
        @SerialName("mainImgUrl")
        val mainImgUrl: String
    )

    fun toEntity() = HomeDataEntity(
        nickname = this.nickname,
        location = this.location,
        recentlyViewedHouses = this.recentlyViewedHouses.map {
            RoomCardEntity(
                houseId = it.houseId,
                monthlyRent = it.monthlyRent,
                deposit = it.deposit,
                occupancyType = it.occupancyTypes,
                location = it.location,
                genderPolicy = it.genderPolicy,
                locationDescription = it.locationDescription,
                isPinned = it.isPinned,
                moodTag = it.moodTag,
                contractTerm = it.contractTerm,
                mainImgUrl = it.mainImgUrl
            )
        }
    )
} 
