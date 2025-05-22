package com.wearerommies.roomie.presentation.ui.map.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.core.component.RoomieTextChip
import com.wearerommies.roomie.presentation.core.extension.customShadow
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun MarkerDetailCard(
    monthlyRent: String,
    deposit: String,
    contractTerm: Int,
    gender: String,
    occupancy: String,
    location: String,
    locationDescription: String,
    moodTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .customShadow(shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .background(RoomieTheme.colors.grayScale1)
            .clickable { onClick() }
            .padding(top = 8.dp, bottom = 16.dp, start = 16.dp, end = 8.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Row {
                    Text(
                        text = stringResource(R.string.monthly_rent),
                        style = RoomieTheme.typography.title2Sb16,
                        color = RoomieTheme.colors.grayScale12
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = monthlyRent,
                        style = RoomieTheme.typography.title2Sb16,
                        color = RoomieTheme.colors.grayScale12
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.deposit_variable, deposit),
                        style = RoomieTheme.typography.body5Sb12,
                        color = RoomieTheme.colors.primary
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(vertical = 3.dp)
                            .background(RoomieTheme.colors.primary)
                            .height(12.dp)
                            .width(1.5.dp)
                    )

                    Text(
                        text = stringResource(R.string.contract_term_variable, contractTerm),
                        style = RoomieTheme.typography.body5Sb12,
                        color = RoomieTheme.colors.primary
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = occupancy,
                        style = RoomieTheme.typography.body4R12,
                        color = RoomieTheme.colors.grayScale7,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_middle_dot),
                        tint = RoomieTheme.colors.grayScale7,
                        contentDescription = null
                    )

                    Text(
                        text = gender,
                        style = RoomieTheme.typography.body4R12,
                        color = RoomieTheme.colors.grayScale7,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = location,
                        style = RoomieTheme.typography.body4R12,
                        color = RoomieTheme.colors.grayScale7,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_middle_dot),
                        tint = RoomieTheme.colors.grayScale7,
                        contentDescription = null
                    )

                    Text(
                        text = locationDescription,
                        style = RoomieTheme.typography.body4R12,
                        color = RoomieTheme.colors.grayScale7,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoomieTextChip(
                    text = moodTag,
                    textStyle = RoomieTheme.typography.body4R12,
                    textColor = RoomieTheme.colors.grayScale9,
                    backgroundColor = RoomieTheme.colors.grayScale3
                )
            }
        }

        Spacer(modifier = Modifier.width(4.dp))

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.btn_like_mapcard),
            contentDescription = stringResource(R.string.navigate_to_house_detail),
            modifier = Modifier
                .padding(8.dp),
            tint = Color.Unspecified
        )
    }
}

@Preview
@Composable
fun MarkerDetailCardPreview() {
    RoomieAndroidTheme {
        MarkerDetailCard(
            monthlyRent = "30~50",
            deposit = "200~300",
            contractTerm = 6,
            gender = "여성전용",
            occupancy = "1인실",
            location = "서대문구 연희동sk스카이뷰12345678912345687912356798123456789123",
            locationDescription = "sk스카이뷰12345678912345687912356798123456789123",
            moodTag = "#활기찬",
            onClick = {}
        )
    }
}
