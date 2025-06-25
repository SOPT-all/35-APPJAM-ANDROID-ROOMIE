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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.wearerommies.roomie.R
import com.wearerommies.roomie.domain.entity.AccountEntity
import com.wearerommies.roomie.presentation.core.component.RoomieButton
import com.wearerommies.roomie.presentation.type.MyAccountType
import com.wearerommies.roomie.presentation.ui.mypage.component.MyTopBar
import com.wearerommies.roomie.presentation.ui.mypage.myaccount.component.MyAccountButton
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun MyAccountRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToName: (String) -> Unit,
    navigateToNickname: (String) -> Unit,
    navigateToBirth: (String) -> Unit,
    navigateToGender: (String) -> Unit,
    navigateToContact: (String) -> Unit,
    viewModel: MyAccountViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val counter by remember { mutableIntStateOf(0) }

    val currentCounter by rememberUpdatedState(counter)

    LaunchedEffect(currentCounter) {
        viewModel.getUserAccountInformation()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is MyAccountSideEffect.NavigateToNickname -> navigateToNickname(sideEffect.nickname)
                    is MyAccountSideEffect.NavigateToBirth -> navigateToBirth(sideEffect.birth)
                    is MyAccountSideEffect.NavigateToContact -> navigateToContact(sideEffect.contact)
                    is MyAccountSideEffect.NavigateToGender -> navigateToGender(sideEffect.gender)
                    is MyAccountSideEffect.NavigateToName -> navigateToName(sideEffect.name)
                }
            }
    }

    MyAccountScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        state = state.uiState,
        navigateToName = viewModel::navigateToName,
        navigateToNickname = viewModel::navigateToNickname,
        navigateToBirth = viewModel::navigateToBirth,
        navigateToGender = viewModel::navigateToGender,
        navigateToContact = viewModel::navigateToContact,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyAccountScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToName: (String) -> Unit,
    navigateToNickname: (String) -> Unit,
    navigateToBirth: (String) -> Unit,
    navigateToGender: (String) -> Unit,
    navigateToContact: (String) -> Unit,
    state: AccountEntity,
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
            MyTopBar(
                navigateUp = navigateUp,
                title = R.string.my_account_information
            )
        }

        item {
            MyAccountButton(
                myAccountType = MyAccountType.ACCOUNT,
            )

            MyAccountButton(
                myAccountType = MyAccountType.NAME,
                userInformation = state.name.orEmpty(),
                onClick = { navigateToName(state.name.orEmpty()) },
            )
            MyAccountButton(
                myAccountType = MyAccountType.NICKNAME,
                userInformation = state.nickname.orEmpty(),
                onClick = { navigateToNickname(state.nickname.orEmpty()) },
            )
            MyAccountButton(
                myAccountType = MyAccountType.BIRTH,
                userInformation = state.birthDate.orEmpty(),
                onClick = { navigateToBirth(state.birthDate.orEmpty()) },
            )
            MyAccountButton(
                myAccountType = MyAccountType.GENDER,
                userInformation = state.gender.orEmpty(),
                onClick = { navigateToGender(state.gender.orEmpty()) },
            )
            MyAccountButton(
                myAccountType = MyAccountType.PHONE_NUMBER,
                userInformation = state.phoneNumber.orEmpty(),
                onClick = { navigateToContact(state.phoneNumber.orEmpty()) },
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
                text = stringResource(R.string.logout),
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
                text = stringResource(R.string.withdraw),
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
            state = AccountEntity(
                nickname = "루미",
                socialType = "KAKAO",
                birthDate = "",
                gender = "",
                name = "",
                phoneNumber = ""
            ),
            navigateToName = {},
            navigateToNickname = {},
            navigateToBirth = {},
            navigateToGender = {},
            navigateToContact = {},
        )
    }
}