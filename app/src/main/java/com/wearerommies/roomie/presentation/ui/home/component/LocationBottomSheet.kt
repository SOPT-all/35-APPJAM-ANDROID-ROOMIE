package com.wearerommies.roomie.presentation.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.wearerommies.roomie.R
import com.wearerommies.roomie.domain.entity.SearchResultEntity
import com.wearerommies.roomie.presentation.core.component.RoomieEmptyView
import com.wearerommies.roomie.presentation.core.component.RoomieLoadingView
import com.wearerommies.roomie.presentation.core.component.RoomieSnackbar
import com.wearerommies.roomie.presentation.core.util.EmptyUiState
import com.wearerommies.roomie.presentation.type.EmptyViewType
import com.wearerommies.roomie.presentation.ui.search.component.SearchResultCard
import com.wearerommies.roomie.presentation.ui.search.component.SearchTextField
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationBottomSheet(
    state: EmptyUiState<PersistentList<SearchResultEntity>>,
    location: String,
    searchKeyword: String,
    snackBarHost: SnackbarHostState,
    setSearchKeyword: (String) -> Unit,
    fetchResult: (String) -> Unit,
    applyUserLocation: (Float, Float, String) -> Unit,
    onDismissRequest: () -> Unit,
    modifier : Modifier = Modifier
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
                modifier = Modifier.fillMaxWidth(),
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

                Spacer(Modifier.height(11.dp))

                Text(
                    text = stringResource(R.string.location_bottom_sheet, location),
                    style = RoomieTheme.typography.body5Sb12,
                    color = RoomieTheme.colors.grayScale10,
                    modifier = Modifier
                        .padding(
                            top = 4.dp,
                            start = 16.dp
                        )
                        .align(Alignment.Start)
                )

                Spacer(Modifier.height(12.dp))

                SearchTextField(
                    textFieldValue = searchKeyword,
                    onValueChange = setSearchKeyword,
                    onClick = fetchResult,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.88f)
                .fillMaxWidth()
        ) {
            when (state) {
                EmptyUiState.Empty -> {
                    RoomieEmptyView(
                        viewType = EmptyViewType.SEARCH,
                        modifier = Modifier.padding(top = (LocalWindowInfo.current.containerSize.height * 0.1795).dp)
                    )
                }

                EmptyUiState.Failure -> {}

                EmptyUiState.Loading -> {
                    RoomieLoadingView()
                }

                is EmptyUiState.Success -> {
                    LazyColumn {
                        items(state.data) { result ->
                            SearchResultCard(
                                location = result.location,
                                address = result.address,
                                roadAddress = result.roadAddress,
                                modifier = Modifier
                                    .padding(horizontal = 12.dp)
                                    .padding(top = 12.dp),
                                onClick = {
                                    applyUserLocation(
                                        result.latitude,
                                        result.longitude,
                                        result.address,
                                    )
                                }
                            )
                        }
                    }
                    Popup(
                        alignment = Alignment.BottomCenter
                    ) {
                        SnackbarHost(hostState = snackBarHost) { snackbarData ->
                            RoomieSnackbar(
                                modifier = Modifier
                                    .padding(
                                        bottom = 8.dp,
                                        start = 12.dp,
                                        end = 12.dp
                                    ),
                                message = snackbarData.visuals.message
                            )
                        }
                    }
                }

                EmptyUiState.Initial -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.weight(1f))

                        Image(
                            painter = painterResource(R.drawable.img_search_place),
                            contentDescription = null,
                            modifier = Modifier
                                .size(160.dp)
                        )

                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = stringResource(R.string.location_bottom_sheet_title),
                            style = RoomieTheme.typography.heading5Sb18,
                            color = RoomieTheme.colors.grayScale12
                        )

                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = stringResource(R.string.location_bottom_sheet_subtitle),
                            style = RoomieTheme.typography.body1R14,
                            color = RoomieTheme.colors.grayScale7
                        )

                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LocationBottomSheetSuccessPreview() {
    RoomieAndroidTheme {
        LocationBottomSheet(
            state = EmptyUiState.Success(
                persistentListOf(
                    SearchResultEntity(
                        longitude = 126.9219f,
                        latitude = 37.5513f,
                        location = "A 공유 오피스",
                        address = "서울 마포구 신촌로 122",
                        roadAddress = "서울 마포구 신촌로 122"
                    ),
                    SearchResultEntity(
                        longitude = 126.9243f,
                        latitude = 37.5559f,
                        location = "B 스터디 카페",
                        address = "서울 서대문구 이화여대길 52",
                        roadAddress = "서울 서대문구 이화여대길 52"
                    )
                )
            ),
            location = "창천동",
            searchKeyword = "",
            setSearchKeyword = {},
            fetchResult = {},
            applyUserLocation = { _, _, _ -> },
            onDismissRequest = {},
            snackBarHost = SnackbarHostState()
        )
    }
}