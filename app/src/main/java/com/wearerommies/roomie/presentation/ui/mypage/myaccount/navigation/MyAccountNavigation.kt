package com.wearerommies.roomie.presentation.ui.mypage.myaccount.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wearerommies.roomie.presentation.navigator.route.MainTabRoute
import com.wearerommies.roomie.presentation.ui.mypage.myaccount.MyAccountRoute

fun NavController.navigateToMyAccount(navOptions: NavOptions) {
    navigate(
        route = MainTabRoute.My,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.myAccountNavGraph(
    paddingValues: PaddingValues,
    navigateToBookmark: () -> Unit
) {
    //todo: route 수정
    composable<MainTabRoute.My> {
        MyAccountRoute(
            paddingValues = paddingValues,
            navigateUp = {},
            navigateToBookmark = navigateToBookmark
        )
    }
}