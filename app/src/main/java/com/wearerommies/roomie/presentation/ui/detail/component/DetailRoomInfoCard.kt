package com.wearerommies.roomie.presentation.ui.detail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.core.extension.roundedBackgroundWithBorder
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun DetailRoomInfoCard(
    roomStatus: Boolean,
    roomName: String,
    occupancyType: Int,
    gender: String,
    deposit: String,
    monthlyRent: String,
    contractPeriod: String,
    managementFee: String,
    onClickDetailRoomInfoCard: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .roundedBackgroundWithBorder(
                cornerRadius = 8.dp,
                backgroundColor = RoomieTheme.colors.grayScale1,
                borderColor = RoomieTheme.colors.grayScale5,
                borderWidth = 1.dp
            )
            .clickable(onClick = onClickDetailRoomInfoCard)
            .padding(16.dp)
    ) {

        Row {
            DetailRoomInfoStatusChip(
                isAvailable = roomStatus
            )

            Spacer(Modifier.weight(1f))


            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right_line_black_24px),
                contentDescription = null,
                tint = RoomieTheme.colors.grayScale10
            )

        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = roomName,
            style = RoomieTheme.typography.heading5Sb18,
            color = RoomieTheme.colors.grayScale11
        )

        Spacer(Modifier.height(16.dp))

        DetailRoomInfoTexts(
            occupancyType = occupancyType,
            gender = gender,
            deposit = deposit,
            monthlyRent = monthlyRent,
            contractPeriod = contractPeriod,
            managementFee = managementFee
        )

    }
}

@Preview
@Composable
fun DetailRoomInfoCardPreview() {
    RoomieAndroidTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DetailRoomInfoCard(
                roomStatus = true,
                roomName = "룸 A",
                onClickDetailRoomInfoCard = {},
                occupancyType = 2,
                gender = "여성",
                deposit = "5,000,000원",
                monthlyRent = "500,000원",
                contractPeriod = "24-12-20",
                managementFee = "100,000원"
            )

            DetailRoomInfoCard(
                roomStatus = false,
                roomName = "룸 A",
                onClickDetailRoomInfoCard = {},
                occupancyType = 2,
                gender = "여성",
                deposit = "5,000,000원",
                monthlyRent = "500,000원",
                contractPeriod = "24-12-20",
                managementFee = "100,000원"
            )
        }
    }
}
