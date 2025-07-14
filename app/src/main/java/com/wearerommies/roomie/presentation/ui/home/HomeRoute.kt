package com.wearerommies.roomie.presentation.ui.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.wearerommies.roomie.R
import com.wearerommies.roomie.domain.entity.HomeDataEntity
import com.wearerommies.roomie.domain.entity.RoomCardEntity
import com.wearerommies.roomie.presentation.core.component.RoomieNavigateButton
import com.wearerommies.roomie.presentation.core.component.RoomieRoomCard
import com.wearerommies.roomie.presentation.core.component.RoomieSnackbar
import com.wearerommies.roomie.presentation.core.component.RoomieTopBar
import com.wearerommies.roomie.presentation.core.extension.bottomBorder
import com.wearerommies.roomie.presentation.core.extension.noRippleClickable
import com.wearerommies.roomie.presentation.core.util.convertDpToFloat
import com.wearerommies.roomie.presentation.type.HomeMoodCardType
import com.wearerommies.roomie.presentation.type.NavigateButtonType
import com.wearerommies.roomie.presentation.ui.home.component.HomeMoodCard
import com.wearerommies.roomie.presentation.ui.home.component.LocationBottomSheet
import com.wearerommies.roomie.presentation.ui.webview.WebViewUrl
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme
import kotlinx.coroutines.launch

object MoodKey {
    const val CALM = "#차분한"
    const val ACTIVE = "#활기찬"
    const val CLEAN = "#깔끔한"
}

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToBookmark: () -> Unit,
    navigateToMood: (String) -> Unit,
    navigateToMap: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    navigateToWebView: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHost = remember { SnackbarHostState() }
    val bottomSheetSnackBarHost = remember { SnackbarHostState() }
    val counter by remember { mutableIntStateOf(0) }

    val currentCounter by rememberUpdatedState(counter)
    val coroutineScope = rememberCoroutineScope()
    var backPressedTime by remember { mutableLongStateOf(0L) }

    BackHandler(enabled = true) {
        if (System.currentTimeMillis() - backPressedTime <= 1000L) {
            (context as Activity).finish()
        } else run {
            viewModel.emitFinishSnackBar()
        }
        backPressedTime = System.currentTimeMillis()
    }

    LaunchedEffect(currentCounter) {
        viewModel.getHomeData()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is HomeSideEffect.SnackBar -> {
                        snackBarHost.currentSnackbarData?.dismiss()
                        coroutineScope.launch {
                            snackBarHost.showSnackbar(
                                message = context.getString(sideEffect.message),
                                duration = SnackbarDuration.Short
                            )
                        }
                    }

                    is HomeSideEffect.BottomSheetSnackBar -> {
                        bottomSheetSnackBarHost.currentSnackbarData?.dismiss()
                        coroutineScope.launch {
                            bottomSheetSnackBarHost.showSnackbar(
                                message = context.getString(sideEffect.message),
                                duration = SnackbarDuration.Short
                            )
                        }
                    }

                    is HomeSideEffect.NavigateToBookMark -> navigateToBookmark()
                    is HomeSideEffect.NavigateToMood -> navigateToMood(sideEffect.moodTag)
                    is HomeSideEffect.NavigateToMap -> navigateToMap()
                    is HomeSideEffect.NavigateToDetail -> navigateToDetail(sideEffect.houseId)
                    is HomeSideEffect.NavigateToWebView -> navigateToWebView(sideEffect.webViewUrl)
                }
            }
    }

    HomeScreen(
        paddingValues = paddingValues,
        snackBarHost = snackBarHost,
        bottomSheetSnackBarHost = bottomSheetSnackBarHost,
        isShowBottomSheet = state.isShowBottomSheet,
        navigateUp = navigateUp,
        navigateToBookmark = viewModel::navigateToBookmark,
        navigateToMood = viewModel::navigateToMood,
        navigateToMap = viewModel::navigateToMap,
        navigateToDetail = viewModel::navigateToDetail,
        navigateToWebView = viewModel::navigateToWebView,
        onLikeClick = viewModel::bookmarkHouse,
        updateBottomSheetState = viewModel::updateBottomSheetState,
        setSearchKeyWord = viewModel::setSearchKeyword,
        fetchSearchResult = viewModel::fetchSearchResult,
        applyUserLocation = viewModel::applyUserLocation,
        state = state.uiState,
        bottomSheetState = state.bottomSheetState
    )
}

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    snackBarHost: SnackbarHostState,
    bottomSheetSnackBarHost: SnackbarHostState,
    isShowBottomSheet: Boolean,
    navigateUp: () -> Unit,
    navigateToBookmark: () -> Unit,
    navigateToMood: (String) -> Unit,
    navigateToMap: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    navigateToWebView: (String) -> Unit,
    onLikeClick: (Long) -> Unit,
    updateBottomSheetState: () -> Unit,
    setSearchKeyWord: (String) -> Unit,
    fetchSearchResult: (String) -> Unit,
    applyUserLocation: (Float, Float, String) -> Unit,
    state: HomeDataEntity,
    bottomSheetState: LocationBottomSheetState,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()
    val isScrollResponsiveDefault by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex == 0 && scrollState.firstVisibleItemScrollOffset < 1
        }
    }
    val topBarBackgroundColor =
        if (!isScrollResponsiveDefault) RoomieTheme.colors.grayScale1 else Color.Transparent
    val topBarBorderColor =
        if (!isScrollResponsiveDefault) RoomieTheme.colors.grayScale4 else Color.Transparent

    Popup(
        alignment = Alignment.BottomCenter
    ) {
        SnackbarHost(hostState = snackBarHost) { snackbarData ->
            RoomieSnackbar(
                modifier = Modifier
                    .padding(
                        bottom = paddingValues.calculateBottomPadding() - 8.dp,
                        start = 12.dp,
                        end = 12.dp
                    ),
                message = snackbarData.visuals.message
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = RoomieTheme.colors.primaryLight4)
    ) {
        LazyColumn(
            state = scrollState,
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding()),
        ) {
            stickyHeader {
                RoomieTopBar(
                    modifier = Modifier
                        .background(color = topBarBackgroundColor)
                        .bottomBorder(
                            color = topBarBorderColor,
                            height = convertDpToFloat(1.dp)
                        )
                        .statusBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 4.dp),
                    backgroundColor = topBarBackgroundColor,
                    leadingIcon = {
                        Row(
                            modifier = Modifier
                                .padding(all = 8.dp)
                                .noRippleClickable {
                                    updateBottomSheetState()
                                },
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = state.location,
                                style = RoomieTheme.typography.title2Sb16,
                                color = RoomieTheme.colors.grayScale12
                            )
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_down_filled_black_16px),
                                contentDescription = stringResource(R.string.home_location)
                            )
                        }
                    },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier
                                .noRippleClickable { navigateToBookmark() }
                                .padding(all = 8.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_heart_line_black_24px),
                            contentDescription = stringResource(R.string.navigate_to_bookmark)
                        )
                    }
                )
            }

            item {
                Spacer(
                    modifier = Modifier
                        .height(14.dp)
                )

                Box {
                    Spacer(
                        modifier = Modifier
                            .padding(top = 62.dp)
                            .fillMaxWidth()
                            .height((LocalConfiguration.current.screenHeightDp * 0.341).dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        RoomieTheme.colors.primary.copy(alpha = 0.5F)
                                    )
                                ),
                                shape = RectangleShape
                            )
                    )

                    Column {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {

                            Image(
                                modifier = Modifier
                                    .width((LocalConfiguration.current.screenWidthDp * 0.556).dp)
                                    .align(Alignment.CenterEnd),
                                painter = painterResource(R.drawable.img_home_character),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )

                            HomeGreetingMessage(
                                modifier = Modifier
                                    .padding(
                                        start = 20.dp
                                    )
                                    .align(Alignment.CenterStart),
                                nickname = state.nickname
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .height(12.dp)
                        )

                        RoomieNavigateButton(
                            modifier = Modifier
                                .padding(horizontal = 20.dp),
                            type = NavigateButtonType.UPDATE,
                            text = stringResource(R.string.home_banner_message),
                            textStyle = RoomieTheme.typography.body3M14,
                            textColor = RoomieTheme.colors.grayScale10,
                            onClick = { navigateToWebView(WebViewUrl.GAME) }
                        )

                        Spacer(
                            modifier = Modifier
                                .height(20.dp)
                        )

                        MoodCardGroup(
                            onCalmClick = { navigateToMood(MoodKey.CALM) },
                            onActiveClick = { navigateToMood(MoodKey.ACTIVE) },
                            onCleanClick = { navigateToMood(MoodKey.CLEAN) },
                        )

                        RecentCardTitle()
                    }
                }
            }

            if (state.recentlyViewedHouses.isEmpty()) {
                item {
                    RecentlyViewedHousesEmptyView()
                }
            } else {
                itemsIndexed(items = state.recentlyViewedHouses) { index, item ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = RoomieTheme.colors.grayScale1)
                    ) {
                        RoomieRoomCard(
                            modifier = Modifier
                                .padding(start = 12.dp, end = 12.dp, bottom = 4.dp),
                            roomCardEntity = RoomCardEntity(
                                houseId = item.houseId,
                                monthlyRent = item.monthlyRent,
                                deposit = item.deposit,
                                occupancyType = item.occupancyType,
                                location = item.location,
                                genderPolicy = item.genderPolicy,
                                locationDescription = item.locationDescription,
                                isPinned = item.isPinned,
                                moodTag = item.moodTag,
                                contractTerm = item.contractTerm,
                                mainImgUrl = item.mainImgUrl
                            ),
                            onClick = {
                                navigateToDetail(item.houseId)
                            },
                            onLikeClick = { onLikeClick(item.houseId) }
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .background(color = RoomieTheme.colors.grayScale1)
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                    )

                    RoomieNavigateButton(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        type = NavigateButtonType.HOME,
                        text = stringResource(R.string.find_more_sharehouses_in_map),
                        onClick = { navigateToMap() }
                    )

                    Spacer(
                        modifier = Modifier
                            .height(24.dp)
                    )
                }
            }
        }

        if (isShowBottomSheet) {
            LocationBottomSheet(
                state = bottomSheetState.searchResults,
                location = state.location,
                searchKeyword = bottomSheetState.searchKeyword,
                snackBarHost = bottomSheetSnackBarHost,
                setSearchKeyword = setSearchKeyWord,
                fetchResult = fetchSearchResult,
                applyUserLocation = applyUserLocation,
                onDismissRequest = updateBottomSheetState
            )
        }

    }


}

@Composable
private fun RecentlyViewedHousesEmptyView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = RoomieTheme.colors.grayScale1),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(
            modifier = Modifier
                .height(76.dp),
        )

        Text(
            text = stringResource(R.string.no_bookmark_property),
            style = RoomieTheme.typography.heading5Sb18,
            color = RoomieTheme.colors.grayScale12
        )

        Spacer(
            modifier = Modifier
                .height(12.dp),
        )

        Text(
            text = stringResource(R.string.press_heart_to_bookmark_property),
            style = RoomieTheme.typography.body1R14,
            color = RoomieTheme.colors.grayScale12
        )

        Spacer(
            modifier = Modifier
                .height(84.dp),
        )
    }
}

@Composable
private fun RecentCardTitle(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = RoomieTheme.colors.grayScale1)
            .padding(horizontal = 12.dp)
    ) {
        Spacer(
            modifier = Modifier
                .height(32.dp)
        )

        Text(
            text = stringResource(R.string.find_recent_room),
            style = RoomieTheme.typography.heading5Sb18,
            color = RoomieTheme.colors.grayScale12
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

    }
}

@Composable
private fun MoodCardGroup(
    onCalmClick: () -> Unit,
    onActiveClick: () -> Unit,
    onCleanClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = RoomieTheme.colors.grayScale1,
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp
                )
            )
            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.find_mood),
                style = RoomieTheme.typography.heading5Sb18,
                color = RoomieTheme.colors.grayScale12
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HomeMoodCard(
                    modifier = Modifier.weight(1f),
                    homeMoodCardType = HomeMoodCardType.CALM,
                    onClick = onCalmClick
                )

                HomeMoodCard(
                    modifier = Modifier.weight(1f),
                    homeMoodCardType = HomeMoodCardType.ACTIVE,
                    onClick = onActiveClick
                )

                HomeMoodCard(
                    modifier = Modifier.weight(1f),
                    homeMoodCardType = HomeMoodCardType.CLEAN,
                    onClick = onCleanClick
                )
            }
        }
    }
}

@Composable
private fun HomeGreetingMessage(
    nickname: String,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = modifier
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = nickname,
                style = RoomieTheme.typography.heading2Sb20,
                color = RoomieTheme.colors.primary
            )

            Text(
                text = stringResource(R.string.user_is),
                style = RoomieTheme.typography.heading2Sb20,
                color = RoomieTheme.colors.grayScale12
            )
        }

        Spacer(
            modifier = Modifier
                .height(4.dp)
        )

        Text(
            modifier = modifier,
            text = stringResource(R.string.welcome_to_roomie),
            style = RoomieTheme.typography.heading2Sb20,
            color = RoomieTheme.colors.grayScale12
        )

        Spacer(
            modifier = Modifier
                .height(12.dp)
        )

        Text(
            modifier = modifier,
            text = stringResource(R.string.roomie_find_perfect_home, nickname),
            style = RoomieTheme.typography.body4R12,
            color = RoomieTheme.colors.grayScale7
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    RoomieAndroidTheme {
        HomeScreen(
            paddingValues = PaddingValues(),
            snackBarHost = remember { SnackbarHostState() },
            isShowBottomSheet = false,
            navigateUp = {},
            navigateToBookmark = {},
            navigateToMood = {},
            navigateToMap = {},
            navigateToDetail = {},
            navigateToWebView = {},
            onLikeClick = {},
            updateBottomSheetState = {},
            state = HomeDataEntity(
                nickname = "닉넴",
                location = "연남동",
                recentlyViewedHouses = listOf(
                    RoomCardEntity(
                        houseId = 1,
                        monthlyRent = "30~50",
                        deposit = "200~300",
                        occupancyType = "2인실",
                        location = "서대문구 연희동",
                        genderPolicy = "여성전용",
                        locationDescription = "자이아파트",
                        isPinned = false,
                        moodTag = "#차분한",
                        contractTerm = 6,
                        mainImgUrl = "https://i.pinimg.com/236x/12/95/67/1295676da767fa8171baf8a307b5786c.jpg"
                    )
                )
            ),
            bottomSheetState = LocationBottomSheetState(),
            fetchSearchResult = {},
            setSearchKeyWord = {},
            applyUserLocation = { _, _, _ -> },
            bottomSheetSnackBarHost = SnackbarHostState()
        )
    }
}
