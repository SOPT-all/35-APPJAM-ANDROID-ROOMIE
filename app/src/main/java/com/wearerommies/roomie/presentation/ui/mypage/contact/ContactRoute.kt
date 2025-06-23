package com.wearerommies.roomie.presentation.ui.mypage.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.core.component.RommieTextField
import com.wearerommies.roomie.presentation.core.component.RoomieButton
import com.wearerommies.roomie.presentation.ui.mypage.my.component.MyTopBar
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun ContactRoute(
    paddingValues: PaddingValues,
    contact: String,
    navigateUp: () -> Unit,
    viewModel: ContactViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val counter by remember { mutableIntStateOf(0) }
    val currentCounter by rememberUpdatedState(counter)

    LaunchedEffect(currentCounter) {
        viewModel.initPhoneNumber(phoneNumber = contact)
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
            }
    }

    ContactScreen(
        paddingValues = paddingValues,
        contact = contact,
        navigateUp = navigateUp,
        state = state,
        onPhoneNumberChanged = viewModel::updatePhoneNumber,
        onClickEditButton = {}
    )

}

@Composable
fun ContactScreen(
    paddingValues: PaddingValues,
    contact: String,
    navigateUp: () -> Unit,
    state: ContactState,
    onPhoneNumberChanged: (String) -> Unit,
    onClickEditButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RoomieTheme.colors.grayScale1)
            .padding(paddingValues)
            .imePadding(),
    ) {
        MyTopBar(
            navigateUp = navigateUp,
            title = R.string.contact_edit
        )

        Spacer(
            modifier = Modifier
                .padding(top = 20.dp)
        )

        RommieTextField(
            paddingValues = PaddingValues(16.dp),
            textFieldValue = contact,
            onValueChange = onPhoneNumberChanged,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                ),
            keyboardType = KeyboardType.Number,
            isValidate = state.isValidated,
            errorMessage = stringResource(R.string.enter_phone_number)
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
fun ContactScreenPreview() {
    RoomieAndroidTheme {
        ContactScreen(
            paddingValues = PaddingValues(),
            contact = "",
            navigateUp = {},
            state = ContactState(),
            onClickEditButton = { },
            onPhoneNumberChanged = {},
        )
    }
}