package com.wearerommies.roomie.presentation.core.util

fun formatPhoneNumber(input: String): String {
    val digits = input.filter { it.isDigit() }

    return when {
        digits.length <= 3 -> digits
        digits.length <= 7 -> "${digits.substring(0, 3)}-${digits.substring(3)}"
        digits.length <= 11 -> "${digits.substring(0, 3)}-${digits.substring(3, 7)}-${digits.substring(7)}"
        else -> "${digits.substring(0, 3)}-${digits.substring(3, 7)}-${digits.substring(7, 11)}"
    }
}
