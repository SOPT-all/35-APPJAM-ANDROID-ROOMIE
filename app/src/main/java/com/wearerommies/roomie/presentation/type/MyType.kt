package com.wearerommies.roomie.presentation.type

import androidx.annotation.StringRes
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.ui.webview.WebViewUrl

enum class MyType (
    @StringRes val title: Int,
    val webViewUrl: String
    ){
    SERVICE(
        title = R.string.service_introduction,
        webViewUrl = WebViewUrl.SERVICE
    ),
    UPDATE(
        title = R.string.recent_updates,
        webViewUrl = WebViewUrl.UPDATE
    ),
    POLICY(
        title = R.string.policy_and_terms,
        webViewUrl = WebViewUrl.POLICY
    ),
}