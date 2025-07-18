package com.wearerommies.roomie.presentation.core.util

import com.wearerommies.roomie.presentation.core.util.UserConstants.NO_DATA
import com.wearerommies.roomie.presentation.type.GenderType

fun formatGender(input: String): String {
    return when (input) {
        GenderType.MALE.name -> "남성"
        GenderType.FEMALE.name -> "여성"
        else -> NO_DATA
    }
}