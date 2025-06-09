package com.wearerommies.roomie.presentation.ui.map.component;

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wearerommies.roomie.R
import com.wearerommies.roomie.domain.entity.FilterResultEntity
import com.wearerommies.roomie.domain.entity.RoomCardEntity
import com.wearerommies.roomie.presentation.core.component.RoomieEmptyView
import com.wearerommies.roomie.presentation.core.component.RoomieRoomCard
import com.wearerommies.roomie.presentation.core.extension.noRippleClickable
import com.wearerommies.roomie.presentation.core.extension.topBorder
import com.wearerommies.roomie.presentation.core.util.convertDpToFloat
import com.wearerommies.roomie.presentation.type.EmptyViewType
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapBottomSheet(
    onLikeClick: (Long) -> Unit,
    navigateToDetail: (Long) -> Unit,
    houseList: PersistentList<FilterResultEntity>,
    isFullSelected: Boolean,
    updateIsFull: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomSheetScaffold(
        modifier = modifier.padding(top = (LocalConfiguration.current.screenHeightDp * 0.151).dp),
        sheetPeekHeight = (LocalConfiguration.current.screenHeightDp * 0.077).dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContainerColor = RoomieTheme.colors.grayScale1,
        sheetShadowElevation = 4.dp,
        sheetDragHandle = {
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .topBorder(convertDpToFloat(1.dp), RoomieTheme.colors.grayScale4)
                        .padding(horizontal = 16.dp)
                        .padding(top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = if (isFullSelected) ImageVector.vectorResource(R.drawable.ic_circle_active_16px)
                        else ImageVector.vectorResource(R.drawable.ic_circle_inactive_16px),
                        tint = Color.Unspecified,
                        modifier = Modifier.noRippleClickable(onClick = updateIsFull),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(R.string.exclude_full),
                        style = RoomieTheme.typography.body1R14,
                        color = RoomieTheme.colors.grayScale10,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
            }
        },
        sheetContent = {
            if (houseList.isEmpty()) {
                RoomieEmptyView(
                    modifier = Modifier
                        .heightIn(max = (LocalConfiguration.current.screenHeightDp * 0.67).dp)
                        .padding(
                            top = (LocalConfiguration.current.screenHeightDp * 0.115).dp,
                            bottom = (LocalConfiguration.current.screenHeightDp * 0.262).dp
                        )
                        .align(Alignment.CenterHorizontally),
                    viewType = EmptyViewType.LIST
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 11.dp, end = 13.dp)
                        .heightIn(max = (LocalConfiguration.current.screenHeightDp * 0.67).dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    itemsIndexed(houseList) { index, item ->
                        RoomieRoomCard(
                            roomCardEntity = RoomCardEntity(
                                houseId = item.houseId,
                                monthlyRent = item.monthlyRent,
                                deposit = item.deposit,
                                occupancyType = item.occupancyTypes,
                                location = item.locationDescription,
                                genderPolicy = item.genderPolicy,
                                locationDescription = item.locationDescription,
                                moodTag = item.moodTag,
                                contractTerm = item.contractTerm,
                                mainImgUrl = item.mainImgUrl,
                                isPinned = item.isPinned
                            ),
                            onClick = { navigateToDetail(item.houseId) },
                            onLikeClick = { onLikeClick(item.houseId) },
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(43.dp))
                    }
                }
            }
        },
        content = {}
    )
}

@Preview
@Composable
fun MapBottomSheetPreview() {
    RoomieAndroidTheme {
        MapBottomSheet(
            onLikeClick = {},
            navigateToDetail = {},
            houseList = persistentListOf(
                FilterResultEntity(
                    houseId = 1,
                    monthlyRent = "30~50",
                    deposit = "200~300",
                    contractTerm = 6,
                    genderPolicy = "여성전용",
                    occupancyTypes = "1,2인실",
                    location = "서대문구 연희동",
                    locationDescription = "자이아파트",
                    moodTag = "#차분한",
                    latitude = 1.2F,
                    longitude = 1.2F,
                    isPinned = false,
                    mainImgUrl = "",
                    excludeFull = false
                )
            ),
            isFullSelected = false,
            updateIsFull = {}
        )
    }
}
