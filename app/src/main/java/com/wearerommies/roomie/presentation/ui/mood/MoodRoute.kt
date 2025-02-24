package com.wearerommies.roomie.presentation.ui.mood

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.wearerommies.roomie.R
import com.wearerommies.roomie.domain.entity.MoodCardEntity
import com.wearerommies.roomie.domain.entity.RoomCardEntity
import com.wearerommies.roomie.presentation.core.component.RoomieFooter
import com.wearerommies.roomie.presentation.core.component.RoomieLoadingView
import com.wearerommies.roomie.presentation.core.component.RoomieRoomCard
import com.wearerommies.roomie.presentation.core.component.RoomieSnackbar
import com.wearerommies.roomie.presentation.core.component.RoomieTopBar
import com.wearerommies.roomie.presentation.core.extension.bottomBorder
import com.wearerommies.roomie.presentation.core.extension.noRippleClickable
import com.wearerommies.roomie.presentation.core.extension.showToast
import com.wearerommies.roomie.presentation.core.util.UiState
import com.wearerommies.roomie.presentation.core.util.convertDpToFloat
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme
import kotlinx.coroutines.launch

@Composable
fun MoodRoute(
    moodTag: String,
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    viewModel: MoodViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHost = remember { SnackbarHostState() }
    val counter by remember { mutableIntStateOf(0) }

    val currentCounter by rememberUpdatedState(counter)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(currentCounter) {
        viewModel.getMoodList(moodTag = moodTag)
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is MoodSideEffect.ShowToast -> context.showToast(message = sideEffect.message)
                    is MoodSideEffect.SnackBar -> {
                        snackBarHost.currentSnackbarData?.dismiss()
                        coroutineScope.launch {
                            snackBarHost.showSnackbar(
                                message = context.getString(sideEffect.message),
                                duration = SnackbarDuration.Short
                            )
                        }
                    }

                    is MoodSideEffect.NavigateToDetail -> navigateToDetail(sideEffect.houseId)
                }
            }
    }

    MoodScreen(
        paddingValues = paddingValues,
        snackBarHost = snackBarHost,
        navigateUp = navigateUp,
        navigateToDetail = viewModel::navigateToDetail,
        moodTag = moodTag,
        onLikeClick = viewModel::bookmarkHouse,
        state = state.uiState
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoodScreen(
    paddingValues: PaddingValues,
    snackBarHost: SnackbarHostState,
    moodTag: String,
    navigateUp: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    onLikeClick: (Long, String) -> Unit,
    state: UiState<MoodCardEntity>,
    modifier: Modifier = Modifier
) {
    val screenWeigth = LocalConfiguration.current.screenWidthDp
    val height = (screenWeigth * 0.5).dp

    Popup(
        alignment = Alignment.BottomCenter
    ) {
        SnackbarHost(hostState = snackBarHost) { snackbarData ->
            RoomieSnackbar(
                modifier = Modifier
                    .padding(
                        bottom = 23.dp,
                        start = 12.dp,
                        end = 12.dp
                    ),
                message = snackbarData.visuals.message
            )
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = RoomieTheme.colors.grayScale1)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        when (state) {
            is UiState.Loading -> {
                item {
                    Box(
                        modifier = Modifier
                            .height((LocalConfiguration.current.screenHeightDp).dp),
                    ) {
                        RoomieLoadingView()
                    }
                }
            }

            is UiState.Failure -> {
                item {
                    Text(
                        modifier = Modifier
                            .noRippleClickable { navigateUp() },
                        text = "데이터 불러오기 실패",
                        textAlign = TextAlign.Center,
                        fontSize = 30.sp
                    )
                }
            }

            is UiState.Success -> {
                stickyHeader {
                    RoomieTopBar(
                        modifier = Modifier
                            .bottomBorder(
                                height = convertDpToFloat(1.dp),
                                color = RoomieTheme.colors.grayScale4
                            ),
                        leadingIcon = {
                            Icon(
                                modifier = Modifier
                                    .noRippleClickable { navigateUp() }
                                    .padding(all = 10.dp),
                                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_line_black_24px),
                                contentDescription = stringResource(R.string.move_back)
                            )
                        },
                        title = moodTag
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        RoomieTheme.colors.primary.copy(alpha = 0.2f),
                                        RoomieTheme.colors.grayScale1,
                                    )
                                ),
                                shape = RectangleShape,
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 10.dp, bottom = 25.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(160.dp)
                                    .padding(end = 10.dp)
                                    .align(Alignment.CenterEnd),
                                painter = when (moodTag) {
                                    stringResource(R.string.hashtag) + stringResource(R.string.mood_tag_calm) -> painterResource(
                                        R.drawable.img_moodview_calm
                                    )

                                    stringResource(R.string.hashtag) + stringResource(R.string.mood_tag_active) -> painterResource(
                                        R.drawable.img_moodview_exciting
                                    )

                                    stringResource(R.string.hashtag) + stringResource(R.string.mood_tag_clean) -> painterResource(
                                        R.drawable.img_moodview_clean
                                    )

                                    else -> painterResource(R.drawable.img_moodview_calm)
                                },
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )

                            MoodHeaderMessage(
                                modifier = Modifier
                                    .padding(
                                        start = 20.dp
                                    )
                                    .align(Alignment.CenterStart),
                                moodTag = state.data.moodTag,
                                mood = when (moodTag) {
                                    stringResource(R.string.hashtag) + stringResource(R.string.mood_tag_calm) -> stringResource(
                                        R.string.mood_calm
                                    )

                                    stringResource(R.string.hashtag) + stringResource(R.string.mood_tag_active) -> stringResource(
                                        R.string.mood_active
                                    )

                                    stringResource(R.string.hashtag) + stringResource(R.string.mood_tag_clean) -> stringResource(
                                        R.string.mood_clean
                                    )

                                    else -> stringResource(R.string.mood_calm)
                                }
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .height(24.dp)
                        )
                    }
                }

                itemsIndexed(items = state.data.houses) { index, item ->
                    RoomieRoomCard(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp, bottom = 4.dp),
                        roomCardEntity = RoomCardEntity(
                            houseId = item.houseId,
                            monthlyRent = item.monthlyRent,
                            deposit = item.deposit,
                            occupancyType = item.occupancyTypes,
                            location = item.location,
                            genderPolicy = item.genderPolicy,
                            locationDescription = item.locationDescription,
                            isPinned = item.isPinned,
                            contractTerm = item.contractTerm,
                            mainImgUrl = item.mainImgUrl
                        ),
                        onClick = { navigateToDetail(item.houseId) },
                        onLikeClick = { onLikeClick(item.houseId, moodTag) }
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .bottomBorder(
                                height = convertDpToFloat(1.dp),
                                color = RoomieTheme.colors.grayScale5
                            )
                    )

                    RoomieFooter()
                }
            }
        }
    }
}

@Composable
private fun MoodHeaderMessage(
    moodTag: String,
    mood: String,
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = moodTag,
                style = RoomieTheme.typography.heading5Sb18,
                color = RoomieTheme.colors.primary
            )

            Text(
                text = stringResource(R.string.find_house),
                style = RoomieTheme.typography.heading5Sb18,
                color = RoomieTheme.colors.grayScale12
            )
        }

        Text(
            modifier = modifier,
            text = stringResource(R.string.recommend_house),
            style = RoomieTheme.typography.heading5Sb18,
            color = RoomieTheme.colors.grayScale12
        )

        Spacer(
            modifier = Modifier
                .height(4.dp)
        )

        Text(
            modifier = modifier
                .padding(bottom = 6.dp),
            text = stringResource(R.string.mood_house_list, mood),
            style = RoomieTheme.typography.body4R12,
            color = RoomieTheme.colors.grayScale8
        )
    }
}

@Preview
@Composable
fun MoodScreenPreview() {
    RoomieAndroidTheme {
        MoodScreen(
            paddingValues = PaddingValues(),
            snackBarHost = remember { SnackbarHostState() },
            navigateUp = {},
            navigateToDetail = {},
            onLikeClick = { _, _ -> },
            moodTag = "차분한",
            state = UiState.Success(
                MoodCardEntity(
                    moodTag = "#차분한",
                    houses = listOf(
                        MoodCardEntity.House(
                            houseId = 1,
                            monthlyRent = "30~50",
                            deposit = "200~300",
                            occupancyTypes = "2인실",
                            location = "서대문구 연희동",
                            genderPolicy = "여성전용",
                            locationDescription = "자이아파트",
                            isPinned = false,
                            contractTerm = 6,
                            mainImgUrl = "https://i.pinimg.com/236x/12/95/67/1295676da767fa8171baf8a307b5786c.jpg"
                        )
                    )
                )
            )
        )
    }
}
