package com.wearerommies.roomie.presentation.ui.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.wearerommies.roomie.domain.entity.FilterEntity
import com.wearerommies.roomie.domain.entity.SearchResultEntity
import com.wearerommies.roomie.presentation.navigator.route.FilterType
import com.wearerommies.roomie.presentation.navigator.route.Route
import com.wearerommies.roomie.presentation.navigator.route.SearchResultType
import com.wearerommies.roomie.presentation.ui.search.SearchRoute
import kotlin.reflect.typeOf

fun NavController.navigateToSearch(
    filter: FilterEntity,
    navOptions: NavOptions? = null
) {
    navigate(
        route = Route.Search(filter),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.searchNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToMap: (FilterEntity,SearchResultEntity) -> Unit
) {
    composable<Route.Search>(
        typeMap = mapOf(
            typeOf<FilterEntity>() to FilterType
        )
    ) { backStackEntry ->
        SearchRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateToMap = navigateToMap,
            filter = backStackEntry.toRoute<Route.Search>().filter
        )
    }
}
