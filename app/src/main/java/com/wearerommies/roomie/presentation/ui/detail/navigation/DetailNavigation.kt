package com.wearerommies.roomie.presentation.ui.detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wearerommies.roomie.presentation.navigator.route.Route

fun NavController.navigateToDetail(navOptions: NavOptions? = null) {
    navigate(
        route = Route.Detail,
        navOptions = navOptions
    )
}

fun NavController.navigateToDetailHouse(navOptions: NavOptions? = null) {
    navigate(
        route = Route.DetailHouse,
        navOptions = navOptions
    )
}

fun NavController.navigateToDetailRoom(navOptions: NavOptions? = null) {
    navigate(
        route = Route.DetailRoom,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.detailNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit
) {
    composable<Route.Detail> {
//        DetailRoute(
//            paddingValues = paddingValues,
//            navigateUp = navigateUp
//        )
    }
    composable<Route.DetailHouse> {
//        DetailHouseRoute(
//            paddingValues = paddingValues,
//            navigateUp = navigateUp
//        )
    }
    composable<Route.DetailRoom> {
//        DetailRoomsImageRoute(
//            paddingValues = paddingValues,
//            navigateUp = navigateUp
//        )
    }
}
