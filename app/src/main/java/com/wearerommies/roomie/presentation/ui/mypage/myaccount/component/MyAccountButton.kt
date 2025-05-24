package com.wearerommies.roomie.presentation.ui.mypage.myaccount.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.type.MyAccountType
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun MyAccountButton(
    myAccountType: MyAccountType,
    userInformation: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .clickable {
                onClick()
            }
            .padding(
                start = 20.dp,
                end = 10.dp,
                top = 12.dp,
                bottom = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(myAccountType.title),
                style = RoomieTheme.typography.body1R14,
                color = RoomieTheme.colors.grayScale7,
            )

            Text(
                text = userInformation,
                style = RoomieTheme.typography.body2Sb14,
                color = RoomieTheme.colors.grayScale12,
            )
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Icon(
            modifier = Modifier
                .padding(all = 10.dp),
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right_line_lightgray_24px),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}