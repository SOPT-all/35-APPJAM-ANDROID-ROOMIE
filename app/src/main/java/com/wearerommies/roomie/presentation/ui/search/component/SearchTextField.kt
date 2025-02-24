package com.wearerommies.roomie.presentation.ui.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.core.extension.noRippleClickable
import com.wearerommies.roomie.presentation.core.extension.roundedBackgroundWithBorder
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun SearchTextField(
    textFieldValue: String,
    onValueChange: (String) -> Unit,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var textFieldState by remember { mutableStateOf(TextFieldValue(textFieldValue)) }
    var isFocused by remember { mutableStateOf(false) }
    var isTrailingIconClicked by remember { mutableStateOf(false) }

    BasicTextField(
        value = textFieldState,
        onValueChange = { newValue ->
            textFieldState = newValue
            onValueChange(newValue.text)
        },
        textStyle = RoomieTheme.typography.title1R16.copy(RoomieTheme.colors.grayScale12),
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
            .roundedBackgroundWithBorder(
                cornerRadius = 8.dp,
                backgroundColor = RoomieTheme.colors.grayScale2,
                borderColor = if (isFocused) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale5,
                borderWidth = 1.dp
            )
            .padding(vertical = 5.dp)
            .padding(start = 15.dp, end = 8.dp),
        cursorBrush = SolidColor(RoomieTheme.colors.primary),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                if (textFieldState.text.isNotEmpty()) {
                    onClick(textFieldState.text)
                    isTrailingIconClicked = true
                }
                focusManager.clearFocus()
            }
        ),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    innerTextField()

                    if (textFieldState.text.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search_textfield_placeholder),
                            color = RoomieTheme.colors.grayScale7,
                            style = RoomieTheme.typography.title1R16
                        )
                    }
                }

                Spacer(modifier = Modifier.width(4.dp))

                if (isTrailingIconClicked) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_delete_20px),
                        tint = Color.Unspecified,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(10.dp)
                            .noRippleClickable {
                                isTrailingIconClicked = false
                                textFieldState = TextFieldValue((""))
                            }
                    )
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_search_24px),
                        tint = Color.Unspecified,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .noRippleClickable {
                                if (textFieldState.text.isNotEmpty()) {
                                    focusManager.clearFocus()
                                    onClick(textFieldState.text)
                                    isTrailingIconClicked = true
                                }
                            }
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun RoomieSearchTextFieldPreview() {
    RoomieAndroidTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SearchTextField(
                textFieldValue = "",
                onClick = {},
                onValueChange = {}
            )

            SearchTextField(
                textFieldValue = "연남동",
                onClick = {},
                onValueChange = {}
            )
        }
    }
}
