package com.wearerommies.roomie.presentation.ui.mypage.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.core.extension.roundedBackgroundWithBorder
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun MyTextField(
    paddingValues: PaddingValues,
    textFieldValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    placeHolder: String = "",
    isValidate: Boolean = true,
    errorMessage: String? = "",
    singleLine: Boolean = true,
    content: @Composable () -> Unit = {}
) {
    var textFieldState by remember { mutableStateOf(TextFieldValue(textFieldValue)) }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    BackHandler {
        focusManager.clearFocus()
    }

    BasicTextField(
        value = textFieldState,
        onValueChange = { newValue ->
            textFieldState = newValue
            onValueChange(newValue.text)
        },
        textStyle = RoomieTheme.typography.body1R14.copy(
            color = RoomieTheme.colors.grayScale12,
        ),
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
            .roundedBackgroundWithBorder(
                cornerRadius = 8.dp,
                backgroundColor = RoomieTheme.colors.grayScale2,
                borderColor = when {
                    !isValidate -> RoomieTheme.colors.actionError
                    isFocused -> RoomieTheme.colors.primary
                    else -> RoomieTheme.colors.grayScale5
                },
                borderWidth = 1.dp
            )
            .padding(paddingValues),
        cursorBrush = SolidColor(RoomieTheme.colors.primary),
        decorationBox = { innerTextField ->
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.TopStart
                ) {
                    innerTextField()

                    if (textFieldState.text.isEmpty()) {
                        Text(
                            text = placeHolder,
                            color = RoomieTheme.colors.grayScale6,
                            style = RoomieTheme.typography.body1R14,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                if (content != {}) {
                    Spacer(modifier = Modifier.width(2.dp))

                    content()
                }
            }
        }
    )
    if (!isValidate)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_warning_14px),
                contentDescription = stringResource(R.string.error),
                tint = Color.Unspecified
            )

            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    style = RoomieTheme.typography.body4R12,
                    color = RoomieTheme.colors.actionError
                )
            }
        }
}