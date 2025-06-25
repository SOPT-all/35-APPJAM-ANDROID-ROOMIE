package com.wearerommies.roomie.presentation.ui.mypage.birth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.wearerommies.roomie.presentation.navigator.route.Route
import com.wearerommies.roomie.presentation.ui.mypage.birth.BirthRoute

fun NavController.navigateToBirth(birth: String, navOptions: NavOptions? = null) {
    navigate(
        route = Route.Birth(
            birth = birth
        ),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.birthNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
) {
    composable<Route.Birth> { backStackEntry ->
        val birth = backStackEntry.toRoute<Route.Birth>().birth
        BirthRoute(
            paddingValues = paddingValues,
            birth = birth,
            navigateUp = navigateUp
        )
    }
}