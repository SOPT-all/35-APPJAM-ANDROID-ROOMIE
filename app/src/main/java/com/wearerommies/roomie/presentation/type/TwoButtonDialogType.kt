package com.wearerommies.roomie.presentation.type

import androidx.annotation.StringRes
import com.wearerommies.roomie.R

enum class TwoButtonDialogType(
    @StringRes val titleRes: Int,
    @StringRes val confirmButtonTextRes: Int,
    @StringRes val dismissButtonTextRes: Int
) {
    LOGOUT(
        titleRes = R.string.logout_dialog_title,
        confirmButtonTextRes = R.string.confirm,
        dismissButtonTextRes = R.string.dismiss
    ),
    WITHDRAW(
        titleRes = R.string.withdraw_dialog_title,
        confirmButtonTextRes = R.string.withdraw,
        dismissButtonTextRes = R.string.withdraw_dismiss
    )
}