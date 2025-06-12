package com.wearerommies.roomie.presentation.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.wearerommies.roomie.R
import com.wearerommies.roomie.domain.entity.DetailEntity
import com.wearerommies.roomie.domain.entity.TourEntity
import com.wearerommies.roomie.presentation.core.component.RoomieButton
import com.wearerommies.roomie.presentation.core.component.RoomieHouseNameChip
import com.wearerommies.roomie.presentation.core.component.RoomieLoadingView
import com.wearerommies.roomie.presentation.core.component.RoomieSnackbar
import com.wearerommies.roomie.presentation.core.component.RoomieTopBar
import com.wearerommies.roomie.presentation.core.extension.bottomBorder
import com.wearerommies.roomie.presentation.core.extension.noRippleClickable
import com.wearerommies.roomie.presentation.core.extension.roundedBackgroundWithBorder
import com.wearerommies.roomie.presentation.core.extension.topBorder
import com.wearerommies.roomie.presentation.core.util.PriceFormatter.formatPriceWon
import com.wearerommies.roomie.presentation.core.util.UiState
import com.wearerommies.roomie.presentation.core.util.convertDpToFloat
import com.wearerommies.roomie.presentation.ui.detail.component.DetailBottomIconButton
import com.wearerommies.roomie.presentation.ui.detail.component.DetailBottomSheet
import com.wearerommies.roomie.presentation.ui.detail.component.DetailContentHeader
import com.wearerommies.roomie.presentation.ui.detail.component.DetailInnerFacilityCard
import com.wearerommies.roomie.presentation.ui.detail.component.DetailMoodCard
import com.wearerommies.roomie.presentation.ui.detail.component.DetailRoomInfoCard
import com.wearerommies.roomie.presentation.ui.detail.component.DetailRoomMateCard
import com.wearerommies.roomie.presentation.ui.webview.WebViewUrl
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

@Composable
fun DetailRoute(
    paddingValues: PaddingValues,
    houseId: Long,
    navigateUp: () -> Unit,
    navigateDetailRoom: (Long, Long, String) -> Unit,
    navigateDetailHouse: (Long, String) -> Unit,
    navigateTourApply: (TourEntity, String, String) -> Unit,
    navigateToWebView: (String) -> Unit,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val counter by remember { mutableIntStateOf(0) }
    val currentCounter by rememberUpdatedState(counter)

    LaunchedEffect(currentCounter) {
        viewModel.getHouseDetail(houseId)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val snackBarHost = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                DetailSideEffect.NavigateUp -> navigateUp()
                is DetailSideEffect.NavigateDetailRoom -> navigateDetailRoom(
                    sideEffect.houseId,
                    sideEffect.roomId,
                    sideEffect.title
                )

                is DetailSideEffect.NavigateDetailHouse -> navigateDetailHouse(
                    sideEffect.houseId,
                    sideEffect.title
                )

                is DetailSideEffect.NavigateTourApply -> navigateTourApply(
                    sideEffect.tourEntity,
                    sideEffect.houseName,
                    sideEffect.roomName
                )

                is DetailSideEffect.NavigateToWebView -> navigateToWebView(sideEffect.webViewUrl)
                is DetailSideEffect.SnackBar -> {
                    snackBarHost.currentSnackbarData?.dismiss()
                    coroutineScope.launch {
                        snackBarHost.showSnackbar(
                            message = context.getString(sideEffect.message),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }

    DetailScreen(
        paddingValues = paddingValues,
        snackBarHost = snackBarHost,
        navigateUp = viewModel::navigateUp,
        navigateDetailRoom = viewModel::navigateToDetail,
        navigateDetailHouse = viewModel::navigateToHouse,
        navigateTourApply = viewModel::navigateToTourApply,
        navigateToWebView = viewModel::navigateToWebView,
        state = state.uiState,
        isShowBottomSheet = state.isShowBottomSheet,
        isLivingExpanded = state.isLivingExpanded,
        isKitchenExpanded = state.isKitchenExpanded,
        isSelectededTourRoom = state.isSelectedTourRoom,
        selectedTourRoom = state.selectedTourRoom,
        selectedTourName = state.selectedTourRoomName,
        updateSelecetedTourRoomId = viewModel::updateSelectedTourRoomId,
        updateBottomSheetState = viewModel::updateBottomSheetState,
        updateLivingExpanded = viewModel::updateLivingExpanded,
        updateKitchenExpanded = viewModel::updatedKitchenExpanded,
        updateSelectedTourRoomName = viewModel::updateSelectedTourRoomName,
        onLikeClick = viewModel::bookmarkHouse
    )
}

@Composable
fun DetailScreen(
    paddingValues: PaddingValues,
    snackBarHost: SnackbarHostState,
    state: UiState<DetailEntity>,
    isShowBottomSheet: Boolean,
    isLivingExpanded: Boolean,
    isKitchenExpanded: Boolean,
    isSelectededTourRoom: Boolean,
    selectedTourRoom: Long,
    selectedTourName: String,
    updateSelecetedTourRoomId: (Long) -> Unit,
    updateBottomSheetState: () -> Unit,
    updateLivingExpanded: () -> Unit,
    updateKitchenExpanded: () -> Unit,
    updateSelectedTourRoomName: (String) -> Unit,
    navigateUp: () -> Unit,
    navigateDetailRoom: (Long, Long, String) -> Unit,
    navigateDetailHouse: (Long, String) -> Unit,
    navigateTourApply: (Long, Long, String, String) -> Unit,
    navigateToWebView: (String) -> Unit,
    onLikeClick: (Long) -> Unit
) {
    val scrollState = rememberLazyListState()
    var imageHeight by remember { mutableIntStateOf(0) }
    var titleHeight by remember { mutableIntStateOf(0) }

    val isScrollResponsiveDefault by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex == 0 && scrollState.firstVisibleItemScrollOffset < imageHeight + titleHeight
        }
    }
    val topBarBackgroundColor =
        if (!isScrollResponsiveDefault) RoomieTheme.colors.grayScale1 else Color.Transparent
    val topBarBorderColor =
        if (!isScrollResponsiveDefault) RoomieTheme.colors.grayScale4 else Color.Transparent
    val topBarBorderHeight =
        if (!isScrollResponsiveDefault) convertDpToFloat(1.dp) else convertDpToFloat(0.dp)
    val topBarTextcolor =
        if (!isScrollResponsiveDefault) RoomieTheme.colors.grayScale12 else Color.Transparent

    Popup(
        alignment = Alignment.BottomCenter
    ) {
        SnackbarHost(hostState = snackBarHost) { snackbarData ->
            RoomieSnackbar(
                modifier = Modifier
                    .padding(
                        bottom = 92.dp,
                        start = 12.dp,
                        end = 12.dp
                    ),
                message = snackbarData.visuals.message
            )
        }
    }

    when (state) {
        UiState.Failure -> TODO()
        UiState.Loading -> {
            RoomieLoadingView()
        }

        is UiState.Success -> {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
            ) {
                RoomieTopBar(
                    backgroundColor = topBarBackgroundColor,
                    title = stringResource(
                        R.string.detail_topbar,
                        state.data.houseInfo.monthlyRent,
                        state.data.houseInfo.deposit
                    ),
                    titleColor = topBarTextcolor,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .zIndex(1F)
                        .bottomBorder(
                            color = topBarBorderColor,
                            height = topBarBorderHeight
                        )
                        .then(
                            if (!isScrollResponsiveDefault) {
                                Modifier
                                    .background(RoomieTheme.colors.grayScale1)
                                    .statusBarsPadding()
                            } else {
                                Modifier.statusBarsPadding()
                            }
                        ),
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(all = 10.dp)
                                .noRippleClickable(navigateUp),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_line_black_24px),
                            contentDescription = stringResource(R.string.move_back)
                        )
                    },
                )

                LazyColumn(
                    state = scrollState,
                    modifier = Modifier
                        .background(RoomieTheme.colors.grayScale1)
                        .fillMaxWidth()
                        .padding(bottom = 80.dp)
                ) {
                    item {

                        Box {
                            AsyncImage(
                                model = state.data.houseInfo.mainImageUrl,
                                contentDescription = stringResource(R.string.house_main_image),
                                modifier = Modifier
                                    .aspectRatio(360f / 304f)
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        imageHeight = coordinates.size.height
                                    },
                                contentScale = ContentScale.Crop
                            )
                            Spacer(
                                Modifier
                                    .fillMaxSize()
                                    .height(20.dp)
                                    .clip(
                                        shape = RoundedCornerShape(
                                            topStart = 12.dp,
                                            topEnd = 12.dp
                                        )
                                    )
                                    .background(RoomieTheme.colors.grayScale1)
                                    .align(Alignment.BottomCenter)
                            )
                        }
                    }

                    item {

                        Column(
                            modifier = Modifier
                                .onGloballyPositioned { coordinates ->
                                    titleHeight = coordinates.size.height
                                }
                                .padding(
                                    horizontal = 16.dp
                                )
                        ) {
                            RoomieHouseNameChip(
                                text = state.data.houseInfo.name
                            )

                            Spacer(Modifier.height(8.dp))

                            Text(
                                text = stringResource(
                                    R.string.house_price_text,
                                    state.data.houseInfo.monthlyRent,
                                    state.data.houseInfo.deposit
                                ),
                                style = RoomieTheme.typography.heading2Sb20,
                                color = RoomieTheme.colors.grayScale11
                            )
                        }
                    }

                    item {

                        val title = stringResource(
                            R.string.detail_topbar,
                            state.data.houseInfo.monthlyRent,
                            state.data.houseInfo.deposit
                        )

                        DetailContentHeader(
                            location = state.data.houseInfo.location,
                            occupancyStatus = state.data.houseInfo.occupancyStatus,
                            contractTerm = state.data.houseInfo.contractTerm,
                            occupancyTypes = state.data.houseInfo.occupancyTypes,
                            genderPolicy = state.data.houseInfo.genderPolicy,
                            onClickDetailInnerButton = {
                                navigateDetailHouse(state.data.houseInfo.houseId, title)
                            }
                        )

                        Spacer(Modifier.height(16.dp))

                        Spacer(
                            modifier = Modifier
                                .height(8.dp)
                                .fillMaxWidth()
                                .background(color = RoomieTheme.colors.grayScale3)
                        )
                    }

                    item {
                        Spacer(Modifier.height(20.dp))

                        Text(
                            text = stringResource(R.string.room_mood),
                            style = RoomieTheme.typography.heading5Sb18,
                            color = RoomieTheme.colors.grayScale12,
                            modifier = Modifier
                                .padding(
                                    horizontal = 16.dp
                                )
                        )

                        Spacer(Modifier.height(16.dp))

                        DetailMoodCard(
                            roomMood = state.data.houseInfo.roomMood,
                            roomMoodTag = state.data.houseInfo.moodTags.toPersistentList(),
                            groundRule = state.data.houseInfo.groundRule.toPersistentList(),
                            modifier = Modifier.padding(
                                horizontal = 16.dp
                            )
                        )
                    }

                    item {
                        Spacer(Modifier.height(40.dp))

                        Text(
                            text = stringResource(R.string.occupancy_status_text),
                            style = RoomieTheme.typography.heading5Sb18,
                            color = RoomieTheme.colors.grayScale12,
                            modifier = Modifier
                                .padding(
                                    horizontal = 16.dp
                                )
                        )

                        Spacer(Modifier.height(16.dp))

                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            val title = stringResource(
                                R.string.detail_topbar,
                                state.data.houseInfo.monthlyRent,
                                state.data.houseInfo.deposit
                            )
                            state.data.rooms.forEach { room ->
                                room.contractPeriod?.let {
                                    DetailRoomInfoCard(
                                        roomStatus = room.status,
                                        roomName = room.name,
                                        occupancyType = room.occupancyType,
                                        gender = room.gender,
                                        deposit = formatPriceWon(room.deposit),
                                        monthlyRent = formatPriceWon(room.monthlyRent),
                                        contractPeriod = it,
                                        managementFee = room.managementFee,
                                        onClickDetailRoomInfoCard = {
                                            navigateDetailRoom(
                                                state.data.houseInfo.houseId,
                                                room.roomId,
                                                title
                                            )
                                        },
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Spacer(Modifier.height(40.dp))

                        Text(
                            text = stringResource(R.string.inner_facilities),
                            style = RoomieTheme.typography.heading5Sb18,
                            color = RoomieTheme.colors.grayScale12,
                            modifier = Modifier
                                .padding(
                                    horizontal = 16.dp
                                )
                        )

                        Spacer(Modifier.height(16.dp))

                        DetailInnerFacilityCard(
                            text = stringResource(R.string.room_safety_living_facility),
                            facility = state.data.houseInfo.safetyLivingFacility.toPersistentList(),
                            onClickExpandedButton = updateLivingExpanded,
                            isExpanded = isLivingExpanded,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        Spacer(Modifier.height(12.dp))

                        DetailInnerFacilityCard(
                            text = stringResource(R.string.room_kitchen_facility),
                            facility = state.data.houseInfo.kitchenFacility.toPersistentList(),
                            onClickExpandedButton = updateKitchenExpanded,
                            isExpanded = isKitchenExpanded,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        Spacer(Modifier.height(32.dp))
                    }
                }

                Row(
                    modifier = Modifier
                        .topBorder(
                            color = RoomieTheme.colors.grayScale5,
                            height = convertDpToFloat(1.dp)
                        )
                        .fillMaxWidth()
                        .background(color = RoomieTheme.colors.grayScale1)
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        )
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DetailBottomIconButton(
                        icon = { isPinned ->
                            Icon(
                                imageVector = if (isPinned) ImageVector.vectorResource(R.drawable.ic_heart_24px_active) else ImageVector.vectorResource(
                                    R.drawable.ic_heart_line_gray_24px
                                ),
                                contentDescription = stringResource(R.string.heart_button),
                                tint = if (isPinned) RoomieTheme.colors.actionError else RoomieTheme.colors.grayScale6,
                            )
                        },
                        onClickButton = { onLikeClick(state.data.houseInfo.houseId) },
                        isPinned = state.data.houseInfo.isPinned
                    )
                    DetailBottomIconButton(
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_inquire_24px),
                                contentDescription = stringResource(R.string.chat_button),
                                tint = RoomieTheme.colors.grayScale6
                            )
                        },
                        onClickButton = { navigateToWebView(WebViewUrl.KAKAO) }
                    )
                    RoomieButton(
                        text = stringResource(R.string.tour_apply_button),
                        backgroundColor = RoomieTheme.colors.primary,
                        textColor = RoomieTheme.colors.grayScale1,
                        onClick = updateBottomSheetState,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        pressedColor = RoomieTheme.colors.primaryLight1,
                        isPressed = true
                    )
                }
                if (isShowBottomSheet) {
                    DetailBottomSheet(
                        rooms = state.data.rooms.toPersistentList(),
                        onDismissRequest = updateBottomSheetState,
                        onOptionButtonClick = { roomId, roomName ->
                            updateSelecetedTourRoomId(roomId)
                            updateSelectedTourRoomName(roomName)
                        },
                        onApplyButtonClick = {
                            navigateTourApply(
                                state.data.houseInfo.houseId,
                                selectedTourRoom,
                                state.data.houseInfo.name,
                                selectedTourName
                            )
                        },
                        selectedTourRoom = selectedTourRoom,
                        isEnabled = isSelectededTourRoom,
                        modifier = Modifier
                            .zIndex(1F)
                            .align(Alignment.BottomCenter)
                            .heightIn(
                                max = paddingValues.calculateBottomPadding() + (LocalConfiguration.current.screenHeightDp * 0.468).dp
                            )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    RoomieAndroidTheme {
        DetailScreen(
            paddingValues = PaddingValues(),
            snackBarHost = remember { SnackbarHostState() },
            navigateUp = {},
            state = UiState.Success(
                DetailEntity(
                    houseInfo = DetailEntity.HouseInfo(
                        houseId = 123L,
                        name = "루미 100호점(이대역)",
                        mainImageUrl = "https://picsum.photos/200",
                        monthlyRent = "43~50",
                        deposit = "90~100",
                        location = "서대문구 연희동",
                        occupancyTypes = "1,2,3인실",
                        occupancyStatus = "2/5",
                        genderPolicy = "여성전용",
                        contractTerm = 3,
                        moodTags = listOf("#차분한", "#유쾌한", "#경쾌한"),
                        groundRule = listOf("요리 후 바로 설거지해요", "청소는 주3회 돌아가면서 해요"),
                        isPinned = true,
                        safetyLivingFacility = listOf("소화기", "소화기"),
                        kitchenFacility = listOf("주걱", "밥솥"),
                        roomMood = "전반적으로 조용하고 깔끔한 환경을 선호하는 아침형 룸메이트들이에요."
                    ),
                    rooms = listOf(
                        DetailEntity.Room(
                            roomId = 1L,
                            name = "1A 싱글침대",
                            status = true,
                            isTourAvailable = true,
                            occupancyType = 2,
                            gender = "여성",
                            deposit = 5000000,
                            monthlyRent = 500000,
                            contractPeriod = "24-12-20",
                            managementFee = "1/n"
                        ),
                        DetailEntity.Room(
                            roomId = 2L,
                            name = "1B 싱글침대",
                            status = false,
                            isTourAvailable = false,
                            occupancyType = 1,
                            gender = "여성",
                            deposit = 5000000,
                            monthlyRent = 500000,
                            contractPeriod = "24-12-20",
                            managementFee = "1/n"
                        )
                    ),
                )
            ),
            isShowBottomSheet = false,
            isLivingExpanded = true,
            isKitchenExpanded = false,
            updateBottomSheetState = {},
            updateLivingExpanded = {},
            updateKitchenExpanded = {},
            navigateDetailRoom = { houseId: Long, roomId: Long, title: String -> },
            navigateDetailHouse = { houseId: Long, title: String -> },
            navigateTourApply = { houseId: Long, roomId: Long, houseName: String, roomName: String -> },
            isSelectededTourRoom = false,
            selectedTourRoom = -1L,
            updateSelecetedTourRoomId = {},
            selectedTourName = "",
            updateSelectedTourRoomName = {},
            navigateToWebView = {},
            onLikeClick = {}
        )
    }
}
