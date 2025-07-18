package com.wearerommies.roomie.presentation.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wearerommies.roomie.presentation.core.extension.roundedBackgroundWithBorder
import com.wearerommies.roomie.presentation.type.TwoButtonDialogType
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun RoomieTwoButtonDialog(
    twoButtonDialogType: TwoButtonDialogType,
    onDismissRequest: () -> Unit,
    onClickConfirm: () -> Unit,
    onClickDismiss: () -> Unit,
) {
    RoomieDialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .roundedBackgroundWithBorder(
                    cornerRadius = 8.dp,
                    backgroundColor = RoomieTheme.colors.grayScale1,
                )
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Text(
                text = stringResource(twoButtonDialogType.titleRes),
                style = RoomieTheme.typography.body6M12,
                color = Color.Black
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp, alignment = Alignment.End),
            ) {
                RoomieButton(
                    verticalPadding = 8.dp,
                    horizontalPadding = 16.dp,
                    text = stringResource(twoButtonDialogType.dismissButtonTextRes),
                    textStyle = RoomieTheme.typography.body5Sb12,
                    textColor = Color.Black,
                    backgroundColor = RoomieTheme.colors.grayScale4,
                    onClick = onClickDismiss,
                )
                RoomieButton(
                    verticalPadding = 8.dp,
                    horizontalPadding = 16.dp,
                    text = stringResource(twoButtonDialogType.confirmButtonTextRes),
                    textStyle = RoomieTheme.typography.body5Sb12,
                    textColor = RoomieTheme.colors.grayScale1,
                    backgroundColor = RoomieTheme.colors.primary,
                    onClick = onClickConfirm,
                )
            }
        }
    }
}

@Preview
@Composable
private fun RoomieTwoButtonDialogPreview() {
    val showTwoButtonDialog = remember { mutableStateOf(false) }

    RoomieAndroidTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = RoomieTheme.colors.grayScale1),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            RoomieButton(
                text = "클릭",
                backgroundColor = RoomieTheme.colors.primary,
                textColor = RoomieTheme.colors.grayScale1,
                onClick = {
                    showTwoButtonDialog.value = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 20.dp),
                pressedColor = RoomieTheme.colors.primaryLight1
            )
        }

        if (showTwoButtonDialog.value) {
            RoomieTwoButtonDialog(
                twoButtonDialogType = TwoButtonDialogType.WITHDRAW,
                onDismissRequest = {
                    showTwoButtonDialog.value = false
                },
                onClickConfirm = {
                    showTwoButtonDialog.value = false
                },
                onClickDismiss = {
                    showTwoButtonDialog.value = false
                }
            )
        }
    }
}
