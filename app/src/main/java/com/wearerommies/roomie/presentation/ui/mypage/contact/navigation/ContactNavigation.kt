package com.wearerommies.roomie.presentation.ui.mypage.nickname.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wearerommies.roomie.presentation.navigator.route.Route

fun NavController.navigateToContact(navOptions: NavOptions? = null) {
    navigate(
        route = Route.Contact,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.contactNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
) {
    composable<Route.Contact> {

    }
}