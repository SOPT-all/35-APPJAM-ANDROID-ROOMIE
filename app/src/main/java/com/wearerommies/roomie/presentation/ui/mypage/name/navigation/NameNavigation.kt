package com.wearerommies.roomie.presentation.ui.mypage.name.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wearerommies.roomie.presentation.navigator.route.Route

fun NavController.navigateToName(navOptions: NavOptions? = null) {
    navigate(
        route = Route.Name,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.nameNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
) {
    composable<Route.Name> {

    }
}