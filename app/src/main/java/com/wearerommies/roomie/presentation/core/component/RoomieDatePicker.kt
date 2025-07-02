package com.wearerommies.roomie.presentation.core.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wearerommies.roomie.R
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Calendar

const val UTC: String = "UTC"

@Composable
fun RoomieDatePicker(
    onConfirm: (selectedDateMillis: Long?) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    inLimited: Boolean = true,
    birthLimited: Boolean = false
) {
    DatePickerModal(
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        inLimited = inLimited,
        modifier = modifier,
        birthLimited = birthLimited
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onConfirm: (Long?) -> Unit,
    onDismiss: () -> Unit,
    inLimited: Boolean,
    modifier: Modifier = Modifier,
    birthLimited: Boolean = false,
) {
    val todayMillisUtc = LocalDate.now().atStartOfDay(ZoneOffset.UTC)?.toInstant()?.toEpochMilli()
        ?: System.currentTimeMillis()

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = todayMillisUtc,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return when {
                    birthLimited -> utcTimeMillis <= todayMillisUtc
                    inLimited -> utcTimeMillis >= todayMillisUtc
                    else -> true
                }
            }

            override fun isSelectableYear(year: Int): Boolean {
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                return when {
                    birthLimited -> year <= currentYear
                    inLimited -> year >= currentYear
                    else -> true
                }
            }
        }
    )

    DatePickerDialog(
        modifier = Modifier.scale(.9f),
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text(
                    text = stringResource(R.string.dismiss),
                    color = RoomieTheme.colors.grayScale11,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(datePickerState.selectedDateMillis) }
            ) {
                Text(
                    text = stringResource(R.string.confirm),
                    color = RoomieTheme.colors.primary,
                )
            }
        },
        colors = DatePickerDefaults.colors(containerColor = RoomieTheme.colors.grayScale1),
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = RoomieTheme.colors.grayScale1,
                disabledSelectedDayContentColor = RoomieTheme.colors.grayScale5,
                selectedDayContainerColor = RoomieTheme.colors.primary,
                selectedDayContentColor = RoomieTheme.colors.grayScale1,
                todayContentColor = RoomieTheme.colors.primary,
                todayDateBorderColor = RoomieTheme.colors.primary,
            )
        )
    }
}

@Preview
@Composable
fun RoomieDatePickerPreview() {
    RoomieAndroidTheme {
        RoomieDatePicker(
            onConfirm = {},
            onDismiss = {},
            inLimited = true,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}

@Preview
@Composable
fun RoomieDatePickerUnlimitedPreview() {
    RoomieAndroidTheme {
        RoomieDatePicker(
            onConfirm = {},
            onDismiss = {},
            inLimited = false,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }
}
