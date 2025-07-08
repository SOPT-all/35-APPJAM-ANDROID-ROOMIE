package com.wearerommies.roomie.presentation.core.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
fun RoomieDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        content()
    }
}
