package com.wearerommies.roomie.presentation.ui.mypage.gender

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import com.wearerommies.roomie.presentation.type.GenderType
import com.wearerommies.roomie.presentation.ui.mypage.my.component.MyTopBar
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun GenderRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    viewModel: GenderViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
            }
    }

    GenderScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        state = state,
        onClickGenderButton = viewModel::updateGender,
        onClickEditButton = {}
    )

}

@Composable
fun GenderScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    state: GenderState,
    onClickGenderButton: (String) -> Unit,
    onClickEditButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RoomieTheme.colors.grayScale1)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MyTopBar(
            navigateUp = navigateUp,
            title = R.string.gender_edit
        )

        Row(
            modifier = Modifier.padding(
                top = 25.dp,
                start = 16.dp,
                end = 16.dp,
            ),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RoomieButton(
                text = stringResource(R.string.man),
                backgroundColor = if (state.uiState.gender == GenderType.MAN.value) RoomieTheme.colors.primaryLight5 else RoomieTheme.colors.grayScale1,
                textColor = if (state.uiState.gender == GenderType.MAN.value) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale12,
                onClick = {
                    onClickGenderButton(GenderType.MAN.value)
                },
                modifier = Modifier
                    .weight(1f),
                borderColor = if (state.uiState.gender == GenderType.MAN.value) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale5,
                borderWidth = 1.dp,
                verticalPadding = 12.dp,
                textStyle = if (state.uiState.gender == GenderType.MAN.value) RoomieTheme.typography.body3M14 else RoomieTheme.typography.body1R14
            )

            RoomieButton(
                text = stringResource(R.string.woman),
                backgroundColor = if (state.uiState.gender == GenderType.WOMAN.value) RoomieTheme.colors.primaryLight5 else RoomieTheme.colors.grayScale1,
                textColor = if (state.uiState.gender == GenderType.WOMAN.value) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale12,
                onClick = {
                    onClickGenderButton(GenderType.WOMAN.value)
                },
                modifier = Modifier
                    .weight(1f),
                borderColor = if (state.uiState.gender == GenderType.WOMAN.value) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale5,
                borderWidth = 1.dp,
                verticalPadding = 12.dp,
                textStyle = if (state.uiState.gender == GenderType.WOMAN.value) RoomieTheme.typography.body3M14 else RoomieTheme.typography.body1R14
            )
        }

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
fun GenderScreenPreview() {
    RoomieAndroidTheme {
        GenderScreen(
            paddingValues = PaddingValues(),
            navigateUp = {},
            state = GenderState(),
            onClickGenderButton = { },
            onClickEditButton = { },
        )
    }
}