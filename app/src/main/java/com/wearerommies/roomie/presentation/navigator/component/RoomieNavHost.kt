package com.wearerommies.roomie.presentation.navigator.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.wearerommies.roomie.presentation.navigator.MainNavigator
import com.wearerommies.roomie.presentation.navigator.route.MainTabRoute
import com.wearerommies.roomie.presentation.ui.home.navigation.homeNavGraph
import com.wearerommies.roomie.presentation.ui.map.navigation.mapNavGraph
import com.wearerommies.roomie.presentation.ui.mypage.navigation.myNavGraph
import com.wearerommies.roomie.presentation.ui.search.navigation.searchNavGraph

@Composable
fun RoomieNavHost(
    navigator: MainNavigator,
    padding: PaddingValues,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navigator.navController,
        startDestination = MainTabRoute.Home
    ) {
        homeNavGraph(
            paddingValues = padding
        )
        mapNavGraph(
            navigateToSearch = navigator::navigateToSearch
        )
        myNavGraph(
            paddingValues = padding
        )
        searchNavGraph(
            paddingValues = padding,
            navigateUp = navigator::popBackStackIfNotHome
        )
    }
}
