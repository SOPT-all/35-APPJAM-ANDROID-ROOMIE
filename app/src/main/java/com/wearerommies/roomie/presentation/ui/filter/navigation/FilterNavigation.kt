package com.wearerommies.roomie.presentation.ui.filter.navigation

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
import com.wearerommies.roomie.presentation.ui.filter.FilterRoute
import kotlin.reflect.typeOf

fun NavController.navigateToFilter(
    filter: FilterEntity,
    searchResult: SearchResultEntity,
    navOptions: NavOptions? = null
) {
    navigate(
        route = Route.Filter(filter, searchResult),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.filterNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToMap: (FilterEntity,SearchResultEntity) -> Unit
) {
    composable<Route.Filter>(
        typeMap = mapOf(
            typeOf<FilterEntity>() to FilterType,
            typeOf<SearchResultEntity>() to SearchResultType
        )
    ) { backStackEntry ->
        FilterRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateToMap = navigateToMap,
            filter = backStackEntry.toRoute<Route.Filter>().filter,
            searchResultEntity = backStackEntry.toRoute<Route.Filter>().result
        )
    }
}
