package com.wearerommies.roomie.presentation.ui.tour.second

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wearerommies.roomie.R
import com.wearerommies.roomie.domain.entity.TourEntity
import com.wearerommies.roomie.presentation.core.component.RoomieButton
import com.wearerommies.roomie.presentation.core.component.RoomieDatePicker
import com.wearerommies.roomie.presentation.core.component.RoomieDatePickerFieldWithTitle
import com.wearerommies.roomie.presentation.core.component.RoomieTextFieldWithTitle
import com.wearerommies.roomie.presentation.core.component.RoomieTopBar
import com.wearerommies.roomie.presentation.core.extension.noRippleClickable
import com.wearerommies.roomie.presentation.type.DatePickerFieldType
import com.wearerommies.roomie.presentation.type.GenderType
import com.wearerommies.roomie.presentation.ui.tour.first.TourFirstSideEffect
import com.wearerommies.roomie.presentation.ui.tour.first.TourFirstState
import com.wearerommies.roomie.presentation.ui.tour.first.TourFirstViewModel
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun TourSecondStepRoute(
    paddingValues: PaddingValues,
    tourApply: TourEntity,
    navigateUp: () -> Unit,
    navigateThirdStep: (TourEntity) -> Unit,
    viewModel: TourSecondViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    val counter by remember { mutableIntStateOf(0) }
    val currentCounter by rememberUpdatedState(counter)

    LaunchedEffect(currentCounter) {
        viewModel.initState(
            tourApply = tourApply
        )
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                TourSecondSideEffect.NavigateUp -> navigateUp()
                is TourSecondSideEffect.NavigateToThirdStep -> navigateThirdStep(sideEffect.tourApply)
            }
        }
    }

    TourSecondStepScreen(
        paddingValues = paddingValues,
        state = state,
        navigateUp = viewModel::navigateUp,
        navigateThirdStep = viewModel::navigateThirdStep,
        onNameChanged = viewModel::updatedName,
        onBirthdateChanged = viewModel::updatedBirthdate,
        onClickGenderButton = viewModel::updatedGender,
        onPhoneNumberChanged = viewModel::updatedPhoneNumber,
        updateDateModalState = viewModel::updateBirthDateModalState
    )
}

@Composable
fun TourSecondStepScreen(
    paddingValues: PaddingValues,
    state: TourSecondState,
    navigateUp: () -> Unit,
    navigateThirdStep: () -> Unit,
    onNameChanged: (String) -> Unit,
    onBirthdateChanged: (Long?) -> Unit,
    onClickGenderButton: (String) -> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    updateDateModalState: () -> Unit,
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
            inLimited = false,
            birthLimited = true
        )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(RoomieTheme.colors.grayScale1)
            .padding(paddingValues)
    ) {
        RoomieTopBar(
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .padding(all = 10.dp)
                        .noRippleClickable(navigateUp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_line_black_24px),
                    contentDescription = stringResource(R.string.move_back)
                )
            }
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.host_connect_title),
            style = RoomieTheme.typography.heading2Sb20,
            color = RoomieTheme.colors.grayScale12,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp
                )
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.private_safety),
            style = RoomieTheme.typography.body4R12,
            color = RoomieTheme.colors.grayScale8,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp
                )
        )

        Spacer(Modifier.height(22.dp))

        RoomieTextFieldWithTitle(
            title = stringResource(R.string.name),
            paddingValues = PaddingValues(16.dp),
            textFieldValue = state.uiState.name,
            onValueChange = onNameChanged,
            color = RoomieTheme.colors.grayScale12,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(28.dp))

        RoomieDatePickerFieldWithTitle(
            viewType = DatePickerFieldType.BIRTHDATE,
            dateValue = state.uiState.birthDate,
            onClick = {
                updateDateModalState()
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(28.dp))

        Text(
            text = stringResource(R.string.gender),
            style = RoomieTheme.typography.body2Sb14,
            color = RoomieTheme.colors.grayScale12,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp
                )
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RoomieButton(
                text = stringResource(R.string.man),
                backgroundColor = if(state.uiState.gender == GenderType.MAN.value) RoomieTheme.colors.primaryLight5 else RoomieTheme.colors.grayScale1,
                textColor = if(state.uiState.gender == GenderType.MAN.value) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale12,
                onClick = {
                    onClickGenderButton(GenderType.MAN.value)
                },
                modifier = Modifier
                    .weight(1f),
                borderColor = if(state.uiState.gender == GenderType.MAN.value) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale5,
                borderWidth = 1.dp,
                verticalPadding = 12.dp,
                textStyle = RoomieTheme.typography.body1R14
            )

            RoomieButton(
                text = stringResource(R.string.woman),
                backgroundColor = if(state.uiState.gender == GenderType.WOMAN.value) RoomieTheme.colors.primaryLight5 else RoomieTheme.colors.grayScale1,
                textColor = if(state.uiState.gender == GenderType.WOMAN.value) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale12,
                onClick = {
                    onClickGenderButton(GenderType.WOMAN.value)
                },
                modifier = Modifier
                    .weight(1f),
                borderColor = if(state.uiState.gender == GenderType.WOMAN.value) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale5,
                borderWidth = 1.dp,
                verticalPadding = 12.dp,
                textStyle = RoomieTheme.typography.body1R14
            )
        }

        Spacer(Modifier.height(28.dp))

        RoomieTextFieldWithTitle(
            title = stringResource(R.string.phone_number),
            paddingValues = PaddingValues(16.dp),
            textFieldValue = state.uiState.phoneNumber,
            onValueChange = onPhoneNumberChanged,
            color = RoomieTheme.colors.grayScale12,
            modifier = Modifier
                .padding(horizontal = 16.dp),
            keyboardType = KeyboardType.Number,
            isValidated = state.isValidated,
            errorMessage = stringResource(R.string.tour_phone_error)
        )

        Spacer(Modifier.weight(1f))

        RoomieButton(
            text = stringResource(R.string.next),
            backgroundColor = RoomieTheme.colors.primary,
            textColor = RoomieTheme.colors.grayScale1,
            onClick = navigateThirdStep,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
            isEnabled = state.isSecondEnabledButton,
            isPressed = true,
            pressedColor = RoomieTheme.colors.primaryLight1,
            enabledColor = RoomieTheme.colors.grayScale6
        )
    }
}

@Preview
@Composable
fun TourSecondStepScreenPreview() {
    RoomieAndroidTheme {
        TourSecondStepScreen(
            paddingValues = PaddingValues(0.dp),
            navigateUp = {},
            navigateThirdStep = {},
            onNameChanged = {},
            onBirthdateChanged = {},
            onClickGenderButton = {},
            onPhoneNumberChanged = {},
            state = TourSecondState(),
            updateDateModalState = {}
        )
    }
}