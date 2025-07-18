package com.wearerommies.roomie.presentation.ui.mypage.my

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.wearerommies.roomie.R
import com.wearerommies.roomie.domain.entity.MyPageEntity
import com.wearerommies.roomie.presentation.core.component.RoomieNavigateButton
import com.wearerommies.roomie.presentation.core.component.RoomieTopBar
import com.wearerommies.roomie.presentation.core.extension.showToast
import com.wearerommies.roomie.presentation.core.extension.topBorder
import com.wearerommies.roomie.presentation.core.util.convertDpToFloat
import com.wearerommies.roomie.presentation.type.MyType
import com.wearerommies.roomie.presentation.type.NavigateButtonType
import com.wearerommies.roomie.presentation.ui.mypage.component.MyButtonWithHelperText
import com.wearerommies.roomie.presentation.ui.mypage.component.MyProfileCard
import com.wearerommies.roomie.presentation.ui.mypage.component.MyTitleBox
import com.wearerommies.roomie.presentation.ui.webview.WebViewUrl
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun MyRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToMyAccount: () -> Unit,
    navigateToBookmark: () -> Unit,
    navigateToWebView: (String) -> Unit,
    viewModel: MyViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val counter by remember { mutableIntStateOf(0) }

    val currentCounter by rememberUpdatedState(counter)

    LaunchedEffect(currentCounter) {
        viewModel.getUserInformation()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is MySideEffect.ShowToast -> context.showToast(message = sideEffect.message)
                    is MySideEffect.NavigateToBookMark -> navigateToBookmark()
                    is MySideEffect.NavigateToMyAccount -> navigateToMyAccount()
                    is MySideEffect.NavigateToWebView -> navigateToWebView(sideEffect.webViewUrl)
                }
            }
    }

    MyScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateToMyAccount = viewModel::navigateToMyAccount,
        navigateToBookmark = viewModel::navigateToBookmark,
        navigateToWebView = viewModel::navigateToWebView,
        state = state.uiState
    )

}

@Composable
fun MyScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToMyAccount: () -> Unit,
    navigateToBookmark: () -> Unit,
    navigateToWebView: (String) -> Unit,
    state: MyPageEntity,
    modifier: Modifier = Modifier
) {
    val screenWeigth = LocalConfiguration.current.screenWidthDp
    val height = (screenWeigth * 0.5).dp

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(RoomieTheme.colors.grayScale1)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        stickyHeader {
            RoomieTopBar(
                title = stringResource(R.string.mypage)
            )
        }

        item {
            MyProfileCard(
                modifier = Modifier
                    .topBorder(
                        convertDpToFloat(1.dp),
                        color = RoomieTheme.colors.grayScale4
                    ),
                profileImgUrl = "",
                nickname = state.nickname,
                onClick = navigateToMyAccount
            )

            Spacer(
                modifier = Modifier
                    .topBorder(
                        convertDpToFloat(1.dp),
                        color = RoomieTheme.colors.grayScale4
                    )
                    .background(color = RoomieTheme.colors.grayScale3)
                    .fillMaxWidth()
                    .height(12.dp)
            )

            MyTitleBox(
                modifier = Modifier
                    .topBorder(
                        convertDpToFloat(1.dp),
                        color = RoomieTheme.colors.grayScale4
                    ),
                text = stringResource(R.string.show_more_roomie)
            )

            RoomieNavigateButton(
                type = NavigateButtonType.MY,
                text = stringResource(R.string.bookmark_list),
                onClick = navigateToBookmark
            )

            MyButtonWithHelperText(
                mainText = stringResource(R.string.find_sharehouses),
                helperText = stringResource(R.string.request_new_room),
                onClick = { navigateToWebView(WebViewUrl.SEARCH) }
            )

            MyButtonWithHelperText(
                mainText = stringResource(R.string.add_new_room),
                helperText = stringResource(R.string.register_sharehouse_owner),
                onClick = { navigateToWebView(WebViewUrl.KAKAO) }
            )

            RoomieNavigateButton(
                type = NavigateButtonType.MY,
                text = stringResource(R.string.send_feedback),
                onClick = { navigateToWebView(WebViewUrl.FEEDBACK) }
            )

            Spacer(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = RoomieTheme.colors.grayScale4)
            )

            MyTitleBox(
                text = stringResource(R.string.service_infomation)
            )

            MyType.entries.forEach { type ->
                RoomieNavigateButton(
                    type = NavigateButtonType.MY,
                    text = stringResource(type.title),
                    onClick = { navigateToWebView(type.webViewUrl) }
                )
            }
        }
    }
}

@Preview
@Composable
fun MyScreenPreview() {
    RoomieAndroidTheme {
        MyScreen(
            paddingValues = PaddingValues(),
            navigateUp = {},
            navigateToMyAccount = {},
            navigateToBookmark = {},
            navigateToWebView = {},
            state = MyPageEntity(
                nickname = "루미",
                socialType = "KAKAO"
            )
        )
    }
}