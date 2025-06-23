package com.wearerommies.roomie.presentation.ui.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.wearerommies.roomie.presentation.navigator.route.Route
import com.wearerommies.roomie.presentation.ui.splash.SplashRoute

fun NavGraphBuilder.splashNavGraph(
    navigateToOnboarding: () -> Unit,
    navigateToHome: () -> Unit
) {
    composable<Route.Splash> {
        SplashRoute(
            navigateToOnboarding = navigateToOnboarding,
            navigateToHome = navigateToHome
        )
    }
}
