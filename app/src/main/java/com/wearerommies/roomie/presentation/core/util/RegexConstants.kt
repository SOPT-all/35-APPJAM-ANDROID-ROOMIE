package com.wearerommies.roomie.presentation.core.util

object RegexConstants {
    val TIME_SPLIT_REGEX = Regex("[:\\-]")
    val DATE_SPLIT_REGEX = Regex("[^\\-]+|-")
}