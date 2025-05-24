package com.wearerommies.roomie.presentation.ui.mypage.gender.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wearerommies.roomie.presentation.navigator.route.Route
import com.wearerommies.roomie.presentation.ui.mypage.gender.GenderRoute

fun NavController.navigateToGender(navOptions: NavOptions? = null) {
    navigate(
        route = Route.Gender,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.genderNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
) {
    composable<Route.Gender> {
        GenderRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp
        )
    }
}