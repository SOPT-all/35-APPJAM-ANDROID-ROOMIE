package com.wearerommies.roomie.presentation.ui.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.wearerommies.roomie.presentation.core.extension.noRippleClickable
import com.wearerommies.roomie.presentation.core.extension.roundedBackgroundWithBorder
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun DetailInnerFacilityCard(
    text: String,
    facility: PersistentList<String>,
    onClickExpandedButton: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    imageContent: @Composable () -> Unit = {},
    isExpanded: Boolean = false
) {
    Column(
        modifier = modifier
            .roundedBackgroundWithBorder(
                cornerRadius = 8.dp,
                backgroundColor = RoomieTheme.colors.grayScale1,
                borderColor = RoomieTheme.colors.grayScale5,
                borderWidth = 1.dp
            )
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .noRippleClickable {
                onClickExpandedButton(isExpanded)
            }
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                style = RoomieTheme.typography.body2Sb14,
                color = RoomieTheme.colors.grayScale10,
                modifier = Modifier.padding(start = 6.dp)
            )

            Spacer(Modifier.weight(1f))

            Icon(
                imageVector = if(isExpanded) ImageVector.vectorResource(R.drawable.ic_arrow_up_line_black_24px) else ImageVector.vectorResource(R.drawable.ic_arrow_down_line_black_24px),
                contentDescription = null,
                tint = RoomieTheme.colors.grayScale10,
                modifier = Modifier.padding(end = 4.dp)
            )

        }

        if(isExpanded) {
            // TODO: 상세보기 내부이미지, 방 이미지 뷰 컴포넌트때 구현 예정
            imageContent()

            Spacer(Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly,
                content = {
                    items(facility) { facilityName ->
                        DetailTextWithCheckIcon(
                            text = facilityName,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun DetailInnerFacilityCardPreview() {
    RoomieAndroidTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DetailInnerFacilityCard(
                text = stringResource(R.string.room_kitchen_facility),
                facility = persistentListOf(
                    "세탁기",
                    "건조기",
                    "에어컨",
                    "선풍기",
                    "청소기",
                    "소화전",
                    "세탁용품",
                    "전기장판",
                    "빨래건조대",
                    "스타일러"
                ),
                onClickExpandedButton = {},
                isExpanded = true
            )

            DetailInnerFacilityCard(
                text = stringResource(R.string.room_kitchen_facility),
                facility = persistentListOf(
                    "세탁기",
                    "건조기",
                    "에어컨",
                    "선풍기",
                    "청소기",
                    "소화전",
                    "세탁용품",
                    "전기장판",
                    "빨래건조대",
                    "스타일러"
                ),
                onClickExpandedButton = {},
                isExpanded = false
            )
        }
    }
}