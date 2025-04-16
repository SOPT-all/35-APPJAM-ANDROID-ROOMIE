package com.wearerommies.roomie.presentation.ui.filter.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.core.component.RoomieDatePicker
import com.wearerommies.roomie.presentation.core.component.RoomieDatePickerField
import com.wearerommies.roomie.presentation.ui.filter.component.ColumnWithTitle
import com.wearerommies.roomie.presentation.ui.filter.component.FilterChip
import com.wearerommies.roomie.presentation.ui.filter.screen.Date.ONEMONTH
import com.wearerommies.roomie.presentation.ui.filter.screen.Date.ONEYEAR
import com.wearerommies.roomie.presentation.ui.filter.screen.Date.SIXMONTH
import com.wearerommies.roomie.presentation.ui.filter.screen.Date.THREEMONTH
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

object Date {
    const val ONEYEAR: Int = 1
    const val ONEMONTH: Int = 1
    const val THREEMONTH: Int = 3
    const val SIXMONTH: Int = 6
}

@Composable
fun ContractTypeScreen(
    isDateModalOpened: Boolean,
    setDateModalVisible: () -> Unit,
    preferredDate: String,
    contractPeriod: PersistentList<Int>,
    setPreferredDate: (Long?) -> Unit,
    setContractPeriod: (PersistentList<Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    if (isDateModalOpened)
        RoomieDatePicker(
            onConfirm = { date ->
                setPreferredDate(date)
                setDateModalVisible()
            },
            onDismiss = setDateModalVisible,
            modifier = Modifier.padding(horizontal = 36.dp)
        )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RoomieTheme.colors.grayScale1)
            .padding(horizontal = 16.dp)
            .padding(top = 28.dp)
    ) {
        ColumnWithTitle(
            title = R.string.desired_move_in_date,
            spacerValue = 12.dp,
            content = {
                RoomieDatePickerField(
                    dateValue = preferredDate,
                    backgroundColor = RoomieTheme.colors.grayScale1,
                    onClick = setDateModalVisible
                )
            }
        )

        Spacer(modifier = Modifier.height(36.dp))

        ColumnWithTitle(
            title = R.string.desired_contract_period,
            spacerValue = 12.dp,
            content = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val contractPeriodOptions = listOf(
                        ONEMONTH,
                        THREEMONTH,
                        SIXMONTH,
                        ONEYEAR
                    )

                    contractPeriodOptions.forEach { option ->
                        val isSelected = contractPeriod.contains(option)
                        FilterChip(
                            text = if (option == ONEYEAR) stringResource(
                                R.string.year,
                                option
                            ) else stringResource(R.string.month, option),
                            isSelected = isSelected,
                            onClick = {
                                setContractPeriod(
                                    if (contractPeriod.contains(option)) {
                                        contractPeriod.remove(option).toPersistentList()
                                    } else {
                                        contractPeriod.add(option).toPersistentList()
                                    }
                                )
                            }
                        )
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun ContractTypeScreenPreview() {
    RoomieAndroidTheme {
        ContractTypeScreen(
            preferredDate = "",
            contractPeriod = persistentListOf(),
            isDateModalOpened = false,
            setDateModalVisible = {},
            setPreferredDate = {},
            setContractPeriod = {},
        )
    }
}

