package com.wearerommies.roomie.domain.entity

data class FilterResultWrapperEntity(
    val code: Int,
    val message: String,
    val result: FilterResultEntity
)
