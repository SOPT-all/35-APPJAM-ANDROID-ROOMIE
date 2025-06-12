package com.wearerommies.roomie.data.dto.response

import com.wearerommies.roomie.domain.entity.DetailEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDetailDto(
    @SerialName("houseInfo")
    val houseInfo: HouseInfo,
    @SerialName("rooms")
    val rooms: List<Room>
) {
    @Serializable
    data class HouseInfo(
        @SerialName("houseId")
        val houseId: Long,
        @SerialName("name")
        val name: String,
        @SerialName("mainImgUrl")
        val mainImgUrl: String,
        @SerialName("monthlyRent")
        val monthlyRent: String,
        @SerialName("deposit")
        val deposit: String,
        @SerialName("location")
        val location: String,
        @SerialName("occupancyTypes")
        val occupancyTypes: String,
        @SerialName("occupancyStatus")
        val occupancyStatus: String,
        @SerialName("genderPolicy")
        val genderPolicy: String,
        @SerialName("contractTerm")
        val contractTerm: Int,
        @SerialName("moodTags")
        val moodTags: List<String>,
        @SerialName("roomMood")
        val roomMood: String,
        @SerialName("groundRule")
        val groundRule: List<String>,
        @SerialName("isPinned")
        val isPinned: Boolean,
        @SerialName("safetyLivingFacility")
        val safetyLivingFacility: List<String>,
        @SerialName("kitchenFacility")
        val kitchenFacility: List<String>
    )

    @Serializable
    data class Room(
        @SerialName("roomId")
        val roomId: Long,
        @SerialName("name")
        val name: String,
        @SerialName("status")
        val status: Boolean,
        @SerialName("isTourAvailable")
        val isTourAvailable: Boolean,
        @SerialName("occupancyType")
        val occupancyType: Int,
        @SerialName("gender")
        val gender: String,
        @SerialName("deposit")
        val deposit: Int,
        @SerialName("monthlyRent")
        val monthlyRent: Int,
        @SerialName("contractPeriod")
        val contractPeriod: String?,
        @SerialName("managementFee")
        val managementFee: String
    )

    fun toEntity() = DetailEntity(
        houseInfo = this.houseInfo.let {
            DetailEntity.HouseInfo(
                houseId = it.houseId,
                name = it.name,
                mainImageUrl = it.mainImgUrl,
                monthlyRent = it.monthlyRent,
                deposit = it.deposit,
                location = it.location,
                occupancyTypes = it.occupancyTypes,
                occupancyStatus = it.occupancyStatus,
                genderPolicy = it.genderPolicy,
                contractTerm = it.contractTerm,
                moodTags = it.moodTags,
                roomMood = it.roomMood,
                groundRule = it.groundRule,
                isPinned = it.isPinned,
                safetyLivingFacility = it.safetyLivingFacility,
                kitchenFacility = it.kitchenFacility
            )
        },
        rooms = this.rooms.map {
            DetailEntity.Room(
                roomId = it.roomId,
                name = it.name,
                status = it.status,
                isTourAvailable = it.isTourAvailable,
                occupancyType = it.occupancyType,
                gender = it.gender,
                deposit = it.deposit,
                monthlyRent = it.monthlyRent,
                contractPeriod = it.contractPeriod,
                managementFee = it.managementFee
            )
        }
    )
}
