package com.wearerommies.roomie.presentation.ui.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.core.component.RoomieOutlinedChip
import com.wearerommies.roomie.presentation.core.extension.roundedBackgroundWithBorder
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun SearchResultCard(
    location: String,
    roadAddress: String,
    address: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .roundedBackgroundWithBorder(
                cornerRadius = 8.dp,
                backgroundColor = RoomieTheme.colors.grayScale1,
                borderColor = RoomieTheme.colors.grayScale5,
                borderWidth = 1.dp
            )
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Text(
            text = location,
            style = RoomieTheme.typography.title2Sb16,
            color = RoomieTheme.colors.grayScale12,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(16.dp))

        SearchResultRow(stringResource(R.string.roadAddress), roadAddress)

        Spacer(modifier = Modifier.height(7.dp))

        SearchResultRow(stringResource(R.string.address), address)
    }
}

@Composable
fun SearchResultRow(chipTitle: String, description: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoomieOutlinedChip(text = chipTitle)

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = description,
            style = RoomieTheme.typography.body4R12,
            color = RoomieTheme.colors.grayScale9,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun SearchResultCardPreview() {
    RoomieAndroidTheme {
        SearchResultCard(
            location = "장소명12345678923132131231123456789123459789",
            address = "서울특별시 서대문구 이화여대길 52",
            roadAddress = "서울특별시 서대문구 대현동 11-1",
            onClick = {}
        )
    }
}
