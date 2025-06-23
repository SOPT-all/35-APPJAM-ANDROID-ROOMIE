package com.wearerommies.roomie.presentation.ui.mypage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.core.extension.roundedBackgroundWithBorder
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun MyProfileCard(
    profileImgUrl: String?,
    nickname: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
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
                top = 24.dp,
                bottom = 24.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (profileImgUrl.isNullOrEmpty()) {
            Image(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                painter = painterResource(R.drawable.img_profile),
                contentDescription = stringResource(R.string.profile_image),
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = profileImgUrl,
                contentDescription = stringResource(R.string.profile_image),
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }



        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = nickname,
                    style = RoomieTheme.typography.title2Sb16,
                    color = RoomieTheme.colors.primary
                )

                Text(
                    text = stringResource(R.string.user_hello),
                    style = RoomieTheme.typography.title2Sb16,
                    color = RoomieTheme.colors.grayScale12
                )
            }

            UserLoginTypeChip()
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

@Composable
private fun UserLoginTypeChip() {
    Text(
        modifier = Modifier
            .roundedBackgroundWithBorder(
                cornerRadius = 50.dp,
                backgroundColor = Color.Transparent,
                borderColor = RoomieTheme.colors.grayScale5,
                borderWidth = 1.dp
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        text = stringResource(R.string.user_login_type),
        style = RoomieTheme.typography.caption1R10,
        color = RoomieTheme.colors.grayScale9,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
private fun MyProfileCardPreview() {
    RoomieAndroidTheme {
        MyProfileCard(
            profileImgUrl = "https://example.com/images/house1.jpg",
            nickname = "이루미",
            onClick = {}
        )
    }
}