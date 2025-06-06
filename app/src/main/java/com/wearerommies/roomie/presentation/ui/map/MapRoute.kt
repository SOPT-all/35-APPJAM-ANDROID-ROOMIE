package com.wearerommies.roomie.presentation.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.wearerommies.roomie.R
import com.wearerommies.roomie.domain.entity.FilterEntity
import com.wearerommies.roomie.domain.entity.FilterResultEntity
import com.wearerommies.roomie.domain.entity.SearchResultEntity
import com.wearerommies.roomie.presentation.core.component.RoomieSnackbar
import com.wearerommies.roomie.presentation.ui.map.component.MapBottomSheet
import com.wearerommies.roomie.presentation.ui.map.component.MapTopBar
import com.wearerommies.roomie.presentation.ui.map.component.MarkerDetailCard
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@Composable
fun MapRoute(
    paddingValues: PaddingValues,
    navigateToSearch: () -> Unit,
    navigateToFilter: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    filterEntity: FilterEntity,
    searchResultEntity: SearchResultEntity,
    viewModel: MapViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHost = remember { SnackbarHostState() }
    val initial by remember { mutableIntStateOf(0) }
    val initialKey by rememberUpdatedState(initial)

    LaunchedEffect(initialKey) {
        viewModel.fetchInitialLocation(searchResultEntity.x, searchResultEntity.y)
        viewModel.fetchFilterAndSearch(filterEntity, searchResultEntity)
        viewModel.fetchHouseList()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is MapSideEffect.SnackBar -> {
                        snackBarHost.currentSnackbarData?.dismiss()
                        coroutineScope.launch {
                            snackBarHost.showSnackbar(
                                message = context.getString(sideEffect.message),
                                duration = SnackbarDuration.Short
                            )
                        }
                    }

                    is MapSideEffect.NavigateToDetail -> navigateToDetail(sideEffect.houseId)
                }
            }
    }

    MapScreen(
        paddingValues = paddingValues,
        navigateToSearch = navigateToSearch,
        navigateToFilter = navigateToFilter,
        navigateToDetail = viewModel::navigateToDetail,
        snackBarHost = snackBarHost,
        isBottomSheetOpened = state.isBottomSheetOpened,
        latitude = searchResultEntity.y,
        longitude = searchResultEntity.x,
        searchKeyword = searchResultEntity.location,
        houseList = state.houseList,
        onMarkerClicked = viewModel::showMarkerDetail,
        markerDetail = state.markerDetail,
        clickedMarkerId = state.clickedMarkerId,
        bookMarkHouse = viewModel::bookmarkHouse,
        resetClickedMarker = viewModel::resetClickedMarker,
        setBottomSheetState = viewModel::setBottomSheetState
    )
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(
    paddingValues: PaddingValues,
    navigateToSearch: () -> Unit,
    navigateToFilter: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    snackBarHost: SnackbarHostState,
    isBottomSheetOpened: Boolean,
    latitude: Float,
    longitude: Float,
    searchKeyword: String,
    houseList: PersistentList<FilterResultEntity>,
    onMarkerClicked: (Long) -> Unit,
    markerDetail: FilterResultEntity,
    clickedMarkerId: Long?,
    bookMarkHouse: (Long) -> Unit,
    resetClickedMarker: () -> Unit,
    setBottomSheetState: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val initialCameraPosition = LatLng(latitude.toDouble(), longitude.toDouble()) // 초기 위치 임시 고정
    val initialZoomLevel = 12.0
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(initialCameraPosition, initialZoomLevel)
    }

    Popup(
        alignment = Alignment.BottomCenter
    ) {
        SnackbarHost(hostState = snackBarHost) { snackbarData ->
            RoomieSnackbar(
                modifier = Modifier
                    .padding(
                        bottom = paddingValues.calculateBottomPadding() - 35.dp,
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
            .background(RoomieTheme.colors.grayScale1)
            .padding(bottom = paddingValues.calculateBottomPadding())
    ) {
        // TODO: 기획-디자인과 카메라 범위 자동 조정 -> 현재는 모든 마커가 나타나도록 조정되어 있음
        LaunchedEffect(houseList) {
            if (houseList.isNotEmpty()) {
                val bounds = LatLngBounds.Builder()
                houseList.forEach { marker ->
                    bounds.include(LatLng(marker.latitude.toDouble(), marker.longitude.toDouble()))
                }

                cameraPositionState.move(
                    CameraUpdate.scrollAndZoomTo(initialCameraPosition, initialZoomLevel)
                )
            }
        }

        NaverMap(
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(isZoomControlEnabled = false, isCompassEnabled = false),
            onMapClick = { _, _ ->
                resetClickedMarker()
                setBottomSheetState(true)
                cameraPositionState.move(
                    CameraUpdate.scrollAndZoomTo(initialCameraPosition, initialZoomLevel)
                        .animate(CameraAnimation.Fly)
                )
            }
        ) {
            houseList.forEach { marker ->
                Marker(
                    state = MarkerState(
                        LatLng(
                            marker.latitude.toDouble(),
                            marker.longitude.toDouble()
                        )
                    ),
                    icon = if (marker.houseId == clickedMarkerId)
                        OverlayImage.fromResource(R.drawable.ic_map_pin_active)
                    else OverlayImage.fromResource(R.drawable.ic_map_pin_normal),
                    onClick = {
                        onMarkerClicked(marker.houseId)
                        setBottomSheetState(false)
                        cameraPositionState.move(
                            CameraUpdate.scrollAndZoomTo(
                                LatLng(marker.latitude.toDouble(), marker.longitude.toDouble()),
                                15.0
                            )
                                .animate(CameraAnimation.Fly) // TODO: 기획-디자인과 카메라 이동 애니메이션 상의
                        )
                        true
                    },
                    width = 36.dp,
                    height = 40.dp
                )
            }
        }

        MapTopBar(
            textfield = searchKeyword,
            onClickSearchTextField = navigateToSearch, // TODO: 이때 결과값을 전달해야함.
            onClickFilterButton = navigateToFilter,
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.TopCenter)
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp)
        )

        if (clickedMarkerId != null)
            MarkerDetailCard(
                monthlyRent = markerDetail.monthlyRent,
                deposit = markerDetail.deposit,
                contractTerm = markerDetail.contractTerm,
                gender = markerDetail.genderPolicy,
                occupancy = markerDetail.occupancyTypes,
                location = markerDetail.location,
                locationDescription = markerDetail.locationDescription,
                moodTag = markerDetail.moodTag,
                onClick = { navigateToDetail(markerDetail.houseId) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
            )

        if (isBottomSheetOpened) MapBottomSheet(
            onLikeClick = bookMarkHouse,
            navigateToDetail = navigateToDetail,
            houseList = houseList
        )
    }
}

@Preview
@Composable
fun MapScreenPreview() {
    RoomieAndroidTheme {
        MapScreen(
            paddingValues = PaddingValues(),
            navigateToSearch = {},
            navigateToFilter = {},
            navigateToDetail = {},
            isBottomSheetOpened = false,
            snackBarHost = SnackbarHostState(),
            latitude = 0f,
            longitude = 0f,
            searchKeyword = "",
            houseList = persistentListOf(),
            onMarkerClicked = {},
            markerDetail = FilterResultEntity(
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
            ),
            clickedMarkerId = null,
            resetClickedMarker = {},
            bookMarkHouse = {},
            setBottomSheetState = {}
        )
    }
}
