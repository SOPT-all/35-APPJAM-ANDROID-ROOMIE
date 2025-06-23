package com.wearerommies.roomie.presentation.ui.mypage.gender

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import com.wearerommies.roomie.presentation.ui.mypage.component.MyEditButton
import com.wearerommies.roomie.presentation.ui.mypage.component.MyTopBar
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun GenderRoute(
    paddingValues: PaddingValues,
    gender: String,
    navigateUp: () -> Unit,
    viewModel: GenderViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val counter by remember { mutableIntStateOf(0) }
    val currentCounter by rememberUpdatedState(counter)

    LaunchedEffect(currentCounter) {
        viewModel.initGender(gender = gender)
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
            }
    }

    GenderScreen(
        paddingValues = paddingValues,
        gender = gender,
        navigateUp = navigateUp,
        state = state,
        onClickGenderButton = viewModel::updateGender,
        onClickEditButton = {}
    )

}

@Composable
fun GenderScreen(
    paddingValues: PaddingValues,
    gender: String,
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
                backgroundColor = if (state.updatedGender == GenderType.MAN.value) RoomieTheme.colors.primaryLight5 else RoomieTheme.colors.grayScale1,
                textColor = if (state.updatedGender == GenderType.MAN.value) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale12,
                onClick = {
                    onClickGenderButton(GenderType.MAN.value)
                },
                modifier = Modifier
                    .weight(1f),
                borderColor = if (state.updatedGender == GenderType.MAN.value) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale5,
                borderWidth = 1.dp,
                verticalPadding = 12.dp,
                textStyle = if (state.updatedGender == GenderType.MAN.value) RoomieTheme.typography.body3M14 else RoomieTheme.typography.body1R14
            )

            RoomieButton(
                text = stringResource(R.string.woman),
                backgroundColor = if (state.updatedGender == GenderType.WOMAN.value) RoomieTheme.colors.primaryLight5 else RoomieTheme.colors.grayScale1,
                textColor = if (state.updatedGender == GenderType.WOMAN.value) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale12,
                onClick = {
                    onClickGenderButton(GenderType.WOMAN.value)
                },
                modifier = Modifier
                    .weight(1f),
                borderColor = if (state.updatedGender == GenderType.WOMAN.value) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale5,
                borderWidth = 1.dp,
                verticalPadding = 12.dp,
                textStyle = if (state.updatedGender == GenderType.WOMAN.value) RoomieTheme.typography.body3M14 else RoomieTheme.typography.body1R14
            )
        }

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
fun GenderScreenPreview() {
    RoomieAndroidTheme {
        GenderScreen(
            paddingValues = PaddingValues(),
            gender = "여성",
            navigateUp = {},
            state = GenderState(),
            onClickGenderButton = { },
            onClickEditButton = { },
        )
    }
}