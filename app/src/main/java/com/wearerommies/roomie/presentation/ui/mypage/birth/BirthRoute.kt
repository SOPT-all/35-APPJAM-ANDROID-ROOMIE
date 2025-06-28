package com.wearerommies.roomie.presentation.ui.mypage.birth

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.core.component.RoomieDatePicker
import com.wearerommies.roomie.presentation.core.component.RoomieDatePickerField
import com.wearerommies.roomie.presentation.ui.mypage.component.MyEditButton
import com.wearerommies.roomie.presentation.ui.mypage.component.MyTopBar
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme
import timber.log.Timber

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
    val counter by remember { mutableIntStateOf(0) }
    val currentCounter by rememberUpdatedState(counter)

    LaunchedEffect(currentCounter) {
        viewModel.initBirth(birth = birth)
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    BirthSideEffect.NavigateUp -> navigateUp()
                }
            }
    }

    BirthScreen(
        paddingValues = paddingValues,
        birth = birth,
        navigateUp = navigateUp,
        state = state,
        onBirthdateChanged = viewModel::updateBirthdate,
        updateDateModalState = viewModel::updateBirthDateModalState,
        onClickEditButton = viewModel::editUserBirth
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
                Timber.tag("birth").d("birth: ${state.birth}, updatedBirth: ${state.updatedBirth}")
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
            dateValue = state.updatedBirth,
            backgroundColor = RoomieTheme.colors.grayScale2,
            onClick = {
                updateDateModalState()
            }
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        MyEditButton(
            isEnabled = state.isEnabled,
            onClick = onClickEditButton
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