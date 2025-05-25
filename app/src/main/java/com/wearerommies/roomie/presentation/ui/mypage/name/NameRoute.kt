package com.wearerommies.roomie.presentation.ui.mypage.name

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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
import com.wearerommies.roomie.presentation.core.component.RoomieButton
import com.wearerommies.roomie.presentation.core.component.RoomieTopBar
import com.wearerommies.roomie.presentation.core.extension.bottomBorder
import com.wearerommies.roomie.presentation.core.extension.noRippleClickable
import com.wearerommies.roomie.presentation.core.extension.showToast
import com.wearerommies.roomie.presentation.core.util.convertDpToFloat
import com.wearerommies.roomie.presentation.type.MyAccountType
import com.wearerommies.roomie.presentation.ui.mypage.my.component.MyTextField
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun NameRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    viewModel: NameViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val counter by remember { mutableIntStateOf(0) }

    val currentCounter by rememberUpdatedState(counter)

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NameSideEffect.ShowToast -> context.showToast(message = sideEffect.message)
                }
            }
    }

    NameScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        state = state,
        onNameChanged = viewModel::updatedName,
        onClickEditButton = {}
    )

}

@Composable
fun NameScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    state: NameState,
    onNameChanged: (String) -> Unit,
    onClickEditButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RoomieTheme.colors.grayScale1)
            .padding(paddingValues),
    ) {
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
            title = stringResource(R.string.name_edit)
        )

        Spacer(
            modifier = Modifier
                .padding(top = 20.dp)
        )

        MyTextField(
            paddingValues = PaddingValues(16.dp),
            textFieldValue = state.uiState.name,
            placeHolder = stringResource(MyAccountType.NAME.title),
            onValueChange = onNameChanged,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                ),
            isValidate = state.isValidated,
            errorMessage = if (state.isEnabled)
                stringResource(R.string.name_error)
            else
                stringResource(R.string.enter_name)
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
            textColor = if (state.isValidated) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale7,
            textStyle = RoomieTheme.typography.body2Sb14,
            onClick = onClickEditButton,
            isEnabled = state.isValidated,
            borderColor = if (state.isValidated) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale5,
            borderWidth = 1.dp
        )
    }
}

@Preview
@Composable
fun NameScreenPreview() {
    RoomieAndroidTheme {
        NameScreen(
            paddingValues = PaddingValues(),
            navigateUp = {},
            state = NameState(),
            onNameChanged = {},
            onClickEditButton = {}
        )
    }
}