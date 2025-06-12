package com.wearerommies.roomie.presentation.ui.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wearerommies.roomie.R
import com.wearerommies.roomie.domain.entity.DetailEntity
import com.wearerommies.roomie.presentation.core.component.RoomieButton
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBottomSheet(
    rooms: PersistentList<DetailEntity.Room>,
    onDismissRequest: () -> Unit,
    onOptionButtonClick: (Long, String) -> Unit,
    onApplyButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = false,
    selectedTourRoom: Long? = null
) {
    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
        },
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        ),
        containerColor = RoomieTheme.colors.grayScale1,
        modifier = modifier
            .fillMaxWidth(),
        dragHandle = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .padding(top = 11.dp, bottom = 14.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(RoomieTheme.colors.grayScale5)
                        .width(36.dp)
                        .height(4.dp)
                )
                Text(
                    text = stringResource(R.string.house),
                    style = RoomieTheme.typography.title2Sb16,
                    color = RoomieTheme.colors.grayScale12,
                    modifier = Modifier.padding(bottom = 9.dp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = RoomieTheme.colors.grayScale4)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            rooms.chunked(2).forEachIndexed { index, rowRooms ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowRooms.forEach { room ->
                        DetailBottomSheetTourOptionButton(
                            roomName = room.name,
                            roomPrice = stringResource(R.string.bottom_sheet_price, (room.deposit / 10000), (room.monthlyRent / 10000)),
                            isSelected = room.roomId == selectedTourRoom,
                            isEnabled = room.isTourAvailable,
                            modifier = Modifier.weight(1f),
                            onButtonClick = {
                                onOptionButtonClick(room.roomId, room.name)
                            }
                        )
                    }
                    if (rowRooms.size % 2 == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            RoomieButton(
                text = stringResource(R.string.tour_apply_button),
                backgroundColor = RoomieTheme.colors.primary,
                enabledColor = RoomieTheme.colors.grayScale6,
                textColor = RoomieTheme.colors.grayScale1,
                onClick = {
                    onApplyButtonClick()
                },
                isEnabled = isEnabled,
                isPressed = true,
                pressedColor = RoomieTheme.colors.primaryLight1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun DetailModalBottomSheetPreview() {
    RoomieAndroidTheme {
        DetailBottomSheet(
            onDismissRequest = {},
            rooms = persistentListOf(
                DetailEntity.Room(
                    roomId = 0L,
                    name = "1A 싱글침대",
                    status = true,
                    isTourAvailable = true,
                    occupancyType = 2,
                    gender = "여성",
                    deposit = 5000000,
                    monthlyRent = 500000,
                    contractPeriod = "24-12-20",
                    managementFee = "1/n"
                ),
                DetailEntity.Room(
                    roomId = 1L,
                    name = "1A 싱글침대",
                    status = true,
                    isTourAvailable = true,
                    occupancyType = 2,
                    gender = "여성",
                    deposit = 5000000,
                    monthlyRent = 500000,
                    contractPeriod = "24-12-20",
                    managementFee = "1/n"
                ),
                DetailEntity.Room(
                    roomId = 2L,
                    name = "1B 싱글침대",
                    status = false,
                    isTourAvailable = false,
                    occupancyType = 1,
                    gender = "여성",
                    deposit = 5000000,
                    monthlyRent = 500000,
                    contractPeriod = "24-12-20",
                    managementFee = "1/n"
                ),

            ),
            onOptionButtonClick = { id: Long, name: String -> },
            onApplyButtonClick = {}
        )
    }
}
