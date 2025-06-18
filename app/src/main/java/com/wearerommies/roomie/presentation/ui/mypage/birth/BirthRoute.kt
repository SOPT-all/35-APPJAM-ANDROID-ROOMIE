package com.wearerommies.roomie.presentation.ui.mypage.birth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.wearerommies.roomie.presentation.core.component.RoomieButton
import com.wearerommies.roomie.presentation.core.component.RoomieDatePicker
import com.wearerommies.roomie.presentation.core.component.RoomieDatePickerField
import com.wearerommies.roomie.presentation.ui.mypage.my.component.MyTopBar
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun BirthRoute(
    paddingValues: PaddingValues,
    birth: String,
    navigateUp: () -> Unit,
    viewModel: BirthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
            }
    }

    BirthScreen(
        paddingValues = paddingValues,
        birth = birth,
        navigateUp = navigateUp,
        state = state,
        onBirthdateChanged = viewModel::updateBirthdate,
        updateDateModalState = viewModel::updateBirthDateModalState,
        onClickEditButton = {}
    )

}

@Composable
fun BirthScreen(
    paddingValues: PaddingValues,
    birth: String,
    navigateUp: () -> Unit,
    state: BirthState,
    onBirthdateChanged: (Long?) -> Unit,
    updateDateModalState: () -> Unit,
    onClickEditButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isShowBirthDateModal)
        RoomieDatePicker(
            onConfirm = { date ->
                onBirthdateChanged(date)
                updateDateModalState()
            },
            onDismiss = {
                updateDateModalState()
            },
            modifier = Modifier.padding(horizontal = 36.dp),
            inLimited = false
        )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RoomieTheme.colors.grayScale1)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MyTopBar(
            navigateUp = navigateUp,
            title = R.string.birth_edit
        )

        RoomieDatePickerField(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 20.dp
                ),
            dateValue = birth,
            backgroundColor = RoomieTheme.colors.grayScale2,
            onClick = {
                updateDateModalState()
            }
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
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
            text = stringResource(R.string.edit),
            backgroundColor = RoomieTheme.colors.grayScale1,
            textColor = if (state.isEnabled) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale7,
            textStyle = RoomieTheme.typography.body2Sb14,
            onClick = onClickEditButton,
            isEnabled = state.isEnabled,
            borderColor = if (state.isEnabled) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale5,
            borderWidth = 1.dp
        )
    }
}

@Preview
@Composable
fun BirthScreenPreview() {
    RoomieAndroidTheme {
        BirthScreen(
            paddingValues = PaddingValues(),
            birth = "",
            navigateUp = {},
            state = BirthState(),
            onClickEditButton = { },
            onBirthdateChanged = { },
            updateDateModalState = {},
        )
    }
}