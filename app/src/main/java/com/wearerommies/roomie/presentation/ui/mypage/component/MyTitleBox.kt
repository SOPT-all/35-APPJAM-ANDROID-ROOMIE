package com.wearerommies.roomie.presentation.ui.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun MyTitleBox(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 4.dp),
        text = text,
        style = RoomieTheme.typography.body1R14,
        color = RoomieTheme.colors.grayScale7,
    )
}

@Preview
@Composable
private fun MyTitleBoxPreview() {
    RoomieAndroidTheme {
        Column(
            modifier = Modifier
                .background(color = RoomieTheme.colors.grayScale1),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            MyTitleBox(
                text = "루미 더보기"
            )
        }
    }
}