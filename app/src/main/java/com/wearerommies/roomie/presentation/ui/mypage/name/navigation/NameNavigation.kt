package com.wearerommies.roomie.presentation.ui.mypage.name.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.wearerommies.roomie.presentation.navigator.route.Route
import com.wearerommies.roomie.presentation.ui.mypage.name.NameRoute

fun NavController.navigateToName(name: String, navOptions: NavOptions? = null) {
    navigate(
        route = Route.Name(
            name = name
        ),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.nameNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
) {
    composable<Route.Name> { backStackEntry ->
        val name = backStackEntry.toRoute<Route.Name>().name
        NameRoute(
            paddingValues = paddingValues,
            name = name,
            navigateUp = navigateUp
        )
    }
}