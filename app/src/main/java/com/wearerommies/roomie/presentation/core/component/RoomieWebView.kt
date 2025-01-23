package com.wearerommies.roomie.presentation.core.component

import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun RoomieWebView(url: String, onClose: () -> Unit) {
    val webViewState = rememberWebViewState(url)
    var webView: android.webkit.WebView? by remember { mutableStateOf(null) }

    BackHandler(enabled = true) {
        if (webView?.canGoBack() == true) {
            webView?.goBack()
        } else {
            onClose()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        WebView(
            state = webViewState,
            modifier = Modifier
                .background(color = RoomieTheme.colors.grayScale1)
                .statusBarsPadding()
                .weight(1f),
            onCreated = { webViewInstance ->
                with(webViewInstance) {
                    settings.run {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        javaScriptCanOpenWindowsAutomatically = false
                    }
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: android.webkit.WebView?, url: String?) {
                            super.onPageFinished(view, url)
                        }
                    }
                }
                webView = webViewInstance
            }
        )
    }
}