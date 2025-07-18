package com.wearerommies.roomie.presentation.ui.webview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wearerommies.roomie.presentation.core.component.RoomieWebView
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme

object WebViewUrl {
    const val QUIZ = "https://smore.im/quiz/mDC9DH57g2"
    const val KAKAO = "https://pf.kakao.com/_WviTn"
}

@Composable
fun WebViewRoute(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    webViewUrl: String
) {

    WebViewScreen(
        paddingValues = paddingValues,
        navigateUp = navigateUp,
        webViewUrl = webViewUrl
    )
}

@Composable
fun WebViewScreen(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    webViewUrl: String
) {
    RoomieWebView(url = webViewUrl, onClose = navigateUp)
}

@Preview
@Composable
fun WebViewScreenPreview() {
    RoomieAndroidTheme {
        WebViewScreen(
            paddingValues = PaddingValues(),
            navigateUp = {},
            webViewUrl = ""
        )
    }
}