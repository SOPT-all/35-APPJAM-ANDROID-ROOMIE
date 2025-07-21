package com.wearerommies.roomie.presentation.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.wearerommies.roomie.R
import com.wearerommies.roomie.domain.entity.FilterEntity
import com.wearerommies.roomie.domain.entity.SearchResultEntity
import com.wearerommies.roomie.presentation.core.component.RoomieEmptyView
import com.wearerommies.roomie.presentation.core.component.RoomieLoadingView
import com.wearerommies.roomie.presentation.core.extension.bottomBorder
import com.wearerommies.roomie.presentation.core.extension.noRippleClickable
import com.wearerommies.roomie.presentation.core.util.EmptyUiState
import com.wearerommies.roomie.presentation.core.util.convertDpToFloat
import com.wearerommies.roomie.presentation.type.EmptyViewType
import com.wearerommies.roomie.presentation.ui.search.component.SearchResultCard
import com.wearerommies.roomie.presentation.ui.search.component.SearchTextField
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SearchRoute(
    paddingValues: PaddingValues,
    filter: FilterEntity,
    navigateUp: () -> Unit,
    navigateToMap: (FilterEntity, SearchResultEntity) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(filter) {
        viewModel.initFilter(filter)
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is SearchSideEffect.navigateToMap -> {
                        navigateToMap(
                            sideEffect.filter,
                            sideEffect.result
                        )
                    }
                }
            }
    }

    SearchScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        state = state.uiState,
        searchKeyword = state.searchKeyword,
        setSearchKeyword = viewModel::setSearchKeyword,
        fetchResult = viewModel::fetchSearchResult,
        applySearchResult = viewModel::applySearchResult
    )

}

@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    state: EmptyUiState<PersistentList<SearchResultEntity>>,
    searchKeyword: String,
    setSearchKeyword: (String) -> Unit,
    fetchResult: (String) -> Unit,
    applySearchResult: (String, String, String, Float?, Float?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(RoomieTheme.colors.grayScale1)
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .bottomBorder(
                    height = convertDpToFloat(1.dp),
                    color = RoomieTheme.colors.grayScale4
                )
                .padding(vertical = 12.dp)
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_line_black_24px),
                contentDescription = stringResource(R.string.move_back),
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(10.dp)
                    .noRippleClickable { navigateUp() }
            )

            Spacer(modifier = Modifier.width(2.dp))

            SearchTextField(
                textFieldValue = searchKeyword,
                onValueChange = setSearchKeyword,
                onClick = fetchResult,
                modifier = Modifier.fillMaxWidth()
            )
        }


        when (state) {
            EmptyUiState.Empty -> {
                RoomieEmptyView(
                    viewType = EmptyViewType.SEARCH,
                    modifier = Modifier.padding(top = (LocalConfiguration.current.screenHeightDp * 0.182).dp)
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
                                .padding(horizontal = 16.dp)
                                .padding(top = 12.dp),
                            onClick = {
                                applySearchResult(
                                    result.location,
                                    result.address,
                                    result.roadAddress,
                                    result.longitude,
                                    result.latitude,
                                )
                            }
                        )
                    }
                }
            }

            EmptyUiState.Initial -> {}
        }
    }
}

@Preview
@Composable
fun SearchScreenEmptyPreview() {
    RoomieAndroidTheme {
        SearchScreen(
            paddingValues = PaddingValues(),
            navigateUp = {},
            state = EmptyUiState.Empty,
            searchKeyword = "",
            setSearchKeyword = {},
            fetchResult = {},
            applySearchResult = { _, _, _, _, _ -> }
        )
    }
}

@Preview
@Composable
fun SearchScreenSuccessPreview() {
    RoomieAndroidTheme {
        SearchScreen(
            paddingValues = PaddingValues(),
            navigateUp = {},
            state = EmptyUiState.Success(
                persistentListOf(
                    SearchResultEntity(
                        longitude = 1.0F,
                        latitude = 1.0F,
                        location = "으아아아아아아",
                        address = "으아아아아아",
                        roadAddress = "으아아아아아"
                    ),
                    SearchResultEntity(
                        longitude = 1.0F,
                        latitude = 1.0F,
                        location = "으아아아아아아아을망라미ㅓ리아ㅓㄹ",
                        address = "으아아아아아",
                        roadAddress = "으아아아아아"
                    ),
                    SearchResultEntity(
                        longitude = 1.0F,
                        latitude = 1.0F,
                        location = "으아아아아ㅣ마어라ㅓㄻ이ㅏ러미아아",
                        address = "으아아아아아",
                        roadAddress = "으아아아아아"
                    )
                )
            ),
            searchKeyword = "",
            setSearchKeyword = {},
            fetchResult = {},
            applySearchResult = { _, _, _, _, _ -> }
        )
    }
}

