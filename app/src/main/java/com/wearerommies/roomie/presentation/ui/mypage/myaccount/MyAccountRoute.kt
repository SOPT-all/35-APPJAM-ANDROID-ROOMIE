package com.wearerommies.roomie.presentation.ui.mypage.myaccount

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.wearerommies.roomie.domain.entity.MyPageEntity
import com.wearerommies.roomie.presentation.core.component.RoomieButton
import com.wearerommies.roomie.presentation.core.component.RoomieTopBar
import com.wearerommies.roomie.presentation.core.extension.bottomBorder
import com.wearerommies.roomie.presentation.core.extension.noRippleClickable
import com.wearerommies.roomie.presentation.core.extension.showToast
import com.wearerommies.roomie.presentation.core.util.convertDpToFloat
import com.wearerommies.roomie.presentation.type.MyAccountType
import com.wearerommies.roomie.presentation.ui.mypage.myaccount.component.MyAccountBox
import com.wearerommies.roomie.presentation.ui.mypage.myaccount.component.MyAccountButton
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun MyAccountRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToBookmark: () -> Unit,
    viewModel: MyAccountViewModel = hiltViewModel()
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
                    is MyAccountSideEffect.ShowToast -> context.showToast(message = sideEffect.message)
                    is MyAccountSideEffect.NavigateToBookMark -> navigateToBookmark()
                }
            }
    }

    MyAccountScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        navigateToBookmark = viewModel::navigateToBookmark,
        state = state.uiState
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyAccountScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToBookmark: () -> Unit,
    state: MyPageEntity,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(RoomieTheme.colors.grayScale1)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
                title = "나의 계정 정보"
            )
        }

        item {
            MyAccountBox()

            MyAccountButton(
                myAccountType = MyAccountType.NAME,
                userInformation = "이루미",
                onClick = {},
            )
            MyAccountButton(
                myAccountType = MyAccountType.NICKNAME,
                userInformation = "카드값줘체리",
                onClick = {},
            )
            MyAccountButton(
                myAccountType = MyAccountType.BIRTH,
                userInformation = "2025.03.21",
                onClick = {},
            )
            MyAccountButton(
                myAccountType = MyAccountType.GENDER,
                userInformation = "여성",
                onClick = {},
            )
            MyAccountButton(
                myAccountType = MyAccountType.PHONE_NUMBER,
                userInformation = "010-0000-0000",
                onClick = {},
            )

            Spacer(
                modifier = Modifier.height(68.dp)
            )

            RoomieButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 8.dp,
                        bottom = 20.dp
                    ),
                verticalPadding = 12.dp,
                text = "로그아웃",
                backgroundColor = RoomieTheme.colors.grayScale1,
                textColor = RoomieTheme.colors.grayScale7,
                textStyle = RoomieTheme.typography.body2Sb14,
                onClick = {
                    //todo: logout
                },
                borderColor = RoomieTheme.colors.grayScale5,
                borderWidth = 1.dp
            )

            Text(
                modifier = Modifier
                    .clickable {
                        //todo: withdraw
                    }
                    .padding(horizontal = 4.dp),
                text = "탈퇴 하기",
                style = RoomieTheme.typography.body5Sb12,
                color = RoomieTheme.colors.grayScale7
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}

@Preview
@Composable
fun MyAccountScreenPreview() {
    RoomieAndroidTheme {
        MyAccountScreen(
            paddingValues = PaddingValues(),
            navigateUp = {},
            navigateToBookmark = {},
            state = MyPageEntity(
                name = "루미"
            )
        )
    }
}