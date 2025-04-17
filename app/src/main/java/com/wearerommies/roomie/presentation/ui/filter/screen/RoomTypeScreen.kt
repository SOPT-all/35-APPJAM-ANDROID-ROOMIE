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
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.ui.filter.component.ColumnWithTitle
import com.wearerommies.roomie.presentation.ui.filter.component.FilterChip
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun RoomTypeScreen(
    genderPolicy: PersistentList<String>,
    occupancyType: PersistentList<String>,
    moodType: PersistentList<String>,
    setGenderPolicy: (PersistentList<String>) -> Unit,
    setOccupancyType: (PersistentList<String>) -> Unit,
    setMoodType: (PersistentList<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RoomieTheme.colors.grayScale1)
            .padding(horizontal = 16.dp)
            .padding(top = 28.dp)
    ) {
        ColumnWithTitle(
            title = R.string.gender,
            spacerValue = 12.dp,
            content = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val genderOptions = listOf(
                        stringResource(R.string.man_only),
                        stringResource(R.string.woman_only),
                        stringResource(R.string.gender_seperate),
                        stringResource(R.string.gender_free)
                    )

                    genderOptions.forEach { option ->
                        val isSelected = genderPolicy.contains(option)
                        FilterChip(
                            text = option,
                            isSelected = isSelected,
                            onClick = {
                                setGenderPolicy(
                                    if (genderPolicy.contains(option)) {
                                        genderPolicy.remove(option).toPersistentList()
                                    } else {
                                        genderPolicy.add(option).toPersistentList()
                                    }
                                )
                            }
                        )
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        ColumnWithTitle(
            title = R.string.room_type,
            spacerValue = 12.dp,
            content = {
                Column {
                    val roomTypeOptions = listOf(
                        stringResource(R.string.one_person),
                        stringResource(R.string.two_person),
                        stringResource(R.string.three_person),
                        stringResource(R.string.more_than_four_person)
                    )

                    roomTypeOptions.chunked(4).forEach { chunk ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            chunk.forEach { option ->
                                val isSelected = occupancyType.contains(option)
                                FilterChip(
                                    text = option,
                                    isSelected = isSelected,
                                    onClick = {
                                        setOccupancyType(
                                            if (occupancyType.contains(option)) {
                                                occupancyType.remove(option).toPersistentList()
                                            } else {
                                                occupancyType.add(option).toPersistentList()
                                            }
                                        )
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        )

        ColumnWithTitle(
            title = R.string.mood_type,
            spacerValue = 12.dp,
            content = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val moodTypeOptions = listOf(
                        stringResource(R.string.mood_tag_calm),
                        stringResource(R.string.mood_tag_active),
                        stringResource(R.string.mood_tag_clean)
                    )

                    moodTypeOptions.forEach { option ->
                        val isSelected = moodType.contains(option)
                        FilterChip(
                            text = option,
                            isSelected = isSelected,
                            onClick = {
                                setMoodType(
                                    if (moodType.contains(option)) {
                                        moodType.remove(option).toPersistentList()
                                    } else {
                                        moodType.add(option).toPersistentList()
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
fun RoomTypeScreenPreview() {
    RoomieAndroidTheme {
        RoomTypeScreen(
            genderPolicy = persistentListOf(),
            occupancyType = persistentListOf(),
            moodType = persistentListOf(),
            setGenderPolicy = {},
            setOccupancyType = {},
            setMoodType = {}
        )
    }
}

