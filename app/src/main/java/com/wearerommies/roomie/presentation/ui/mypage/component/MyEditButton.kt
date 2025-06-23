package com.wearerommies.roomie.presentation.ui.mypage.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.core.component.RoomieButton
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun MyEditButton(
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    RoomieButton(
        modifier = modifier
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
        textColor = if (isEnabled) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale7,
        textStyle = RoomieTheme.typography.body2Sb14,
        onClick = onClick,
        isEnabled = isEnabled,
        borderColor = if (isEnabled) RoomieTheme.colors.primary else RoomieTheme.colors.grayScale5,
        borderWidth = 1.dp
    )
}

@Preview
@Composable
private fun MyButtonPreview() {
    RoomieAndroidTheme {
        MyEditButton(
            isEnabled = true,
            onClick = {}
        )
    }
}