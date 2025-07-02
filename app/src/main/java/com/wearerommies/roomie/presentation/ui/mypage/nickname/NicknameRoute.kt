package com.wearerommies.roomie.presentation.ui.mypage.nickname

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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
import com.wearerommies.roomie.presentation.core.component.RommieTextField
import com.wearerommies.roomie.presentation.core.extension.advancedImePadding
import com.wearerommies.roomie.presentation.type.MyAccountType
import com.wearerommies.roomie.presentation.ui.mypage.component.MyEditButton
import com.wearerommies.roomie.presentation.ui.mypage.component.MyTopBar
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun NicknameRoute(
    paddingValues: PaddingValues,
    nickname: String,
    navigateUp: () -> Unit,
    viewModel: NicknameViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val counter by remember { mutableIntStateOf(0) }
    val currentCounter by rememberUpdatedState(counter)

    LaunchedEffect(currentCounter) {
        viewModel.initNickname(nickname = nickname)
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when(sideEffect) {
                    NicknameSideEffect.NavigateUp -> navigateUp()
                }
            }
    }

    NicknameScreen(
        paddingValues = paddingValues,
        nickname = nickname,
        navigateUp = navigateUp,
        state = state,
        onNicknameChanged = viewModel::updateNickname,
        onClickEditButton = viewModel::editUserNickname
    )

}

@Composable
fun NicknameScreen(
    paddingValues: PaddingValues,
    nickname: String,
    navigateUp: () -> Unit,
    state: NicknameState,
    onNicknameChanged: (String) -> Unit,
    onClickEditButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RoomieTheme.colors.grayScale1)
            .padding(paddingValues)
            .advancedImePadding(),
    ) {
        MyTopBar(
            navigateUp = navigateUp,
            title = R.string.nickname_edit
        )

        Spacer(
            modifier = Modifier
                .padding(top = 20.dp)
        )

        RommieTextField(
            paddingValues = PaddingValues(16.dp),
            textFieldValue = nickname,
            placeHolder = stringResource(MyAccountType.NICKNAME.title),
            onValueChange = onNicknameChanged,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                ),
            textStyle = RoomieTheme.typography.body1R14,
            placeHolderStyle = RoomieTheme.typography.body1R14,
            placeHolderColor = RoomieTheme.colors.grayScale6,
            isValidate = state.isValidated,
            errorMessage = when {
                state.updatedNickname.isEmpty() -> stringResource(R.string.enter_nickname)
                !state.isValidated -> stringResource(R.string.nickname_error)
                else -> ""
            }
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        MyEditButton(
            isEnabled = state.isButtonEnabled,
            onClick = onClickEditButton
        )
    }
}

@Preview
@Composable
fun NicknameScreenPreview() {
    RoomieAndroidTheme {
        NicknameScreen(
            paddingValues = PaddingValues(),
            nickname = "",
            navigateUp = {},
            state = NicknameState(),
            onNicknameChanged = {},
            onClickEditButton = {}
        )
    }
}