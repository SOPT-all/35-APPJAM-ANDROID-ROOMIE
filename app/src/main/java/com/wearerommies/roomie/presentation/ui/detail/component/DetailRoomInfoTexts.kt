package com.wearerommies.roomie.presentation.ui.detail.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.core.util.RegexConstants
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme
import kotlinx.collections.immutable.toPersistentList

@Composable
fun DetailRoomInfoTexts(
    occupancyType: Int,
    gender: String,
    deposit: String,
    monthlyRent: String,
    contractPeriod: String,
    managementFee: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {

            DetailRoomTypeText(
                occupancyType = occupancyType,
                gender = gender
            )

            DetailRoomContractPeriodText(
                contractPeriod = contractPeriod
            )

            DetailRoomPriceText(
                textTitle = R.string.room_management_fee,
                price = managementFee
            )

        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {

            DetailRoomPriceText(
                textTitle = R.string.room_deposit,
                price = deposit
            )

            DetailRoomPriceText(
                textTitle = R.string.room_monthly_rent,
                price = monthlyRent
            )
        }
    }
}

@Composable
fun DetailRoomTypeText(
    occupancyType: Int,
    gender: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.room_shape),
            style = RoomieTheme.typography.body3M14,
            color = RoomieTheme.colors.grayScale8,
            modifier = Modifier.weight(1f)
        )

        Row(
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = stringResource(R.string.room_occupany_type, occupancyType),
                style = RoomieTheme.typography.body1R14,
                color = RoomieTheme.colors.grayScale10
            )

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_middle_dot),
                contentDescription = null,
                tint = RoomieTheme.colors.grayScale10,
                modifier = Modifier
                    .padding(vertical = 9.dp, horizontal = 4.dp)
            )

            Text(
                text = gender,
                style = RoomieTheme.typography.body1R14,
                color = RoomieTheme.colors.grayScale10
            )
        }
    }
}

@Composable
fun DetailRoomPriceText(
    @StringRes textTitle: Int,
    price: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(textTitle),
            style = RoomieTheme.typography.body3M14,
            color = RoomieTheme.colors.grayScale8,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = price,
            style = RoomieTheme.typography.body1R14,
            color = RoomieTheme.colors.grayScale10,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun DetailRoomContractPeriodText(
    contractPeriod: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.contract_period),
            style = RoomieTheme.typography.body3M14,
            color = RoomieTheme.colors.grayScale8,
            modifier = Modifier.weight(1f)
        )

        val dateList = RegexConstants.DATE_SPLIT_REGEX.findAll(contractPeriod).map{
            it.value
        }.toPersistentList()

        Row(
            modifier = Modifier.weight(1f)
        ) {
            dateList.forEach { date ->
                Text(
                    text = date,
                    style = RoomieTheme.typography.body1R14,
                    color = RoomieTheme.colors.grayScale10
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailRoomInfoTextsPreview() {
    RoomieAndroidTheme {
        DetailRoomInfoTexts(
            occupancyType = 2,
            gender = "여성",
            deposit = "5,000,000원",
            monthlyRent = "500,000원",
            contractPeriod = "24-12-20",
            managementFee = "100,000원"
        )

    }
}
