package com.wearerommies.roomie.presentation.ui.filter

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class FilterState(
    val isDateModalOpened: Boolean = false,
    val location: String = "서울특별시 마포구 노고산동",
    val moodTag: String? = null,
    val depositStart: String = "",
    val depositEnd: String = "",
    val monthlyRentStart: String = "",
    val monthlyRentEnd: String = "",
    val genderPolicy: PersistentList<String> = persistentListOf(),
    val preferredDate: String = "",
    val occupancyType: PersistentList<String> = persistentListOf(),
    val moodType: PersistentList<String> = persistentListOf(),
    val contractType: PersistentList<Int> = persistentListOf()
)
