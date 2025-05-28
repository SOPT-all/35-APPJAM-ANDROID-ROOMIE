package com.wearerommies.roomie.presentation.ui.onboarding.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wearerommies.roomie.presentation.navigator.route.Route
import com.wearerommies.roomie.presentation.ui.onboarding.OnboardingRoute

fun NavController.navigateToOnboarding(navOptions: NavOptions? = null) {
    navigate(
        route = Route.Onboarding,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.onboardingNavGraph(
    paddingValues: PaddingValues,
    navigateToLogin:()->Unit
) {
    composable<Route.Onboarding> {
        OnboardingRoute(
            paddingValues = paddingValues,
            navigateToLogin = navigateToLogin
        )
    }
}
