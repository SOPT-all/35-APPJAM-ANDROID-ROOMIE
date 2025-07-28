package com.wearerommies.roomie.presentation.ui.map.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.wearerommies.roomie.domain.entity.FilterEntity
import com.wearerommies.roomie.domain.entity.SearchResultEntity
import com.wearerommies.roomie.presentation.navigator.route.FilterType
import com.wearerommies.roomie.presentation.navigator.route.MainTabRoute
import com.wearerommies.roomie.presentation.navigator.route.SearchResultType
import com.wearerommies.roomie.presentation.ui.map.MapRoute
import kotlin.reflect.typeOf

fun NavController.navigateToMap(
    filter: FilterEntity,
    searchResult: SearchResultEntity,
    navOptions: NavOptions? = null
) {
    navigate(
        route = MainTabRoute.Map(filter, searchResult),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.mapNavGraph(
    paddingValues: PaddingValues,
    navigateToSearch: (FilterEntity) -> Unit,
    navigateToFilter: (FilterEntity, SearchResultEntity) -> Unit,
    navigateToDetail: (Long) -> Unit
) {
    composable<MainTabRoute.Map>(
        typeMap = mapOf(
            typeOf<FilterEntity>() to FilterType,
            typeOf<SearchResultEntity>() to SearchResultType
        )
    ) { backStackEntry ->
        MapRoute(
            paddingValues = paddingValues,
            navigateToSearch = navigateToSearch,
            navigateToFilter = navigateToFilter,
            navigateToDetail = navigateToDetail,
            filterEntity = backStackEntry.toRoute<MainTabRoute.Map>().filter,
            searchResultEntity = backStackEntry.toRoute<MainTabRoute.Map>().result
        )
    }
}
