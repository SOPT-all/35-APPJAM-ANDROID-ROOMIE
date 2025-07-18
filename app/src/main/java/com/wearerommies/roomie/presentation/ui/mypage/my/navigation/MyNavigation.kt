package com.wearerommies.roomie.presentation.ui.mypage.my.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wearerommies.roomie.presentation.navigator.route.MainTabRoute
import com.wearerommies.roomie.presentation.ui.mypage.my.MyRoute

fun NavController.navigateToMy(navOptions: NavOptions) {
    navigate(
        route = MainTabRoute.My,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.myNavGraph(
    paddingValues: PaddingValues,
    navigateToMyAccount: () -> Unit,
    navigateToBookmark: () -> Unit,
    navigateToWebView: (String) -> Unit,
) {
    composable<MainTabRoute.My> {
        MyRoute(
            paddingValues = paddingValues,
            navigateUp = {},
            navigateToMyAccount = navigateToMyAccount,
            navigateToBookmark = navigateToBookmark,
            navigateToWebView = navigateToWebView
        )
    }
}