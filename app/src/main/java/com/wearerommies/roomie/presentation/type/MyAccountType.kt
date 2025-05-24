package com.wearerommies.roomie.presentation.type

import androidx.annotation.StringRes
import com.wearerommies.roomie.R

enum class MyAccountType(
    @StringRes val title: Int,
) {
    NAME(
        title = R.string.name
    ),
    NICKNAME(
        title = R.string.user_nickname
    ),
    BIRTH(
        title = R.string.date_birth
    ),
    GENDER(
        title = R.string.gender
    ),
    PHONE_NUMBER(
        title = R.string.phone_number
    )
}