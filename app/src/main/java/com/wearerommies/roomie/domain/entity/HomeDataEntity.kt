package com.wearerommies.roomie.domain.entity

data class HomeDataEntity(
    val nickname: String,
    val location: String,
    val recentlyViewedHouses: List<RoomCardEntity>
)