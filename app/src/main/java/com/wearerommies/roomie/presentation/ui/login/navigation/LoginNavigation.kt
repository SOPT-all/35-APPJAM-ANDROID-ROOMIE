package com.wearerommies.roomie.presentation.ui.login.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wearerommies.roomie.presentation.navigator.route.Route
import com.wearerommies.roomie.presentation.ui.login.LoginRoute

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    navigate(
        route = Route.Login,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.loginNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
) {
    composable<Route.Login> {
        LoginRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateToHome = navigateToHome
        )
    }
}