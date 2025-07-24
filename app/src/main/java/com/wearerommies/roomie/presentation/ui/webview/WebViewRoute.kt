package com.wearerommies.roomie.presentation.ui.webview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wearerommies.roomie.presentation.core.component.RoomieWebView
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme

object WebViewUrl {
    const val QUIZ = "https://smore.im/quiz/mDC9DH57g2"
    const val KAKAO_APP = "kakaoplus://plusfriend/friend/_WviTn"
    const val KAKAO_WEB = "https://pf.kakao.com/_WviTn"
    const val SEARCH = "https://tally.so/r/3y8ygX"
    const val FEEDBACK = "https://tally.so/r/megeRl"
    const val SERVICE = "https://automatic-protocol-11a.notion.site/23336a29f06280a394ceea5f51b2d09b"
    const val UPDATE = "https://automatic-protocol-11a.notion.site/23336a29f06280e4b956f3dc6bf963bc"
    const val POLICY = "https://automatic-protocol-11a.notion.site/23336a29f06280d5a4f0c622c73a5121"
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