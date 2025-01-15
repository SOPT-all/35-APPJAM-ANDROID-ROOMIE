package com.wearerommies.roomie.presentation.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.wearerommies.roomie.presentation.navigator.route.MainTabRoute
import com.wearerommies.roomie.presentation.navigator.route.Route
import com.wearerommies.roomie.presentation.type.MainTabType
import com.wearerommies.roomie.presentation.ui.filter.navigation.navigateToFilter
import com.wearerommies.roomie.presentation.ui.home.navigation.navigateToHome
import com.wearerommies.roomie.presentation.ui.map.navigation.navigateToMap
import com.wearerommies.roomie.presentation.ui.mypage.navigation.navigateToMy
import com.wearerommies.roomie.presentation.ui.navigateToBookmark
import com.wearerommies.roomie.presentation.ui.navigateToDetail
import com.wearerommies.roomie.presentation.ui.navigateToDetailAllImage
import com.wearerommies.roomie.presentation.ui.navigateToDetailRoomsImage
import com.wearerommies.roomie.presentation.ui.navigateToMood
import com.wearerommies.roomie.presentation.ui.search.navigation.navigateToSearch
import com.wearerommies.roomie.presentation.ui.tour.navigation.navigateToTour

class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = MainTabRoute.Home

    val currentTab: MainTabType?
        @Composable get() = MainTabType.find { tab ->
            currentDestination?.route == tab::class.qualifiedName
        }

    fun navigate(tab: MainTabType) {
        val navOptions = navOptions {
            popUpTo<MainTabRoute.Home> {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTabType.HOME -> navController.navigateToHome(navOptions)
            MainTabType.MAP -> navController.navigateToMap(navOptions)
            MainTabType.MY -> navController.navigateToMy(navOptions)
        }
    }

    private fun navigateUp() {
        navController.navigateUp()
    }

    fun popBackStackIfNotHome() {
        if (!isSameCurrentDestination<MainTabRoute.Home>()) {
            navigateUp()
        }
    }

    fun navigateToSearch() {
        navController.navigateToSearch()
    }

    fun navigateToMood() {
        navController.navigateToMood()
    }

    fun navigateToBookmark() {
        navController.navigateToBookmark()
    }

    fun navigateToFilter() {
        navController.navigateToFilter()
    }

    fun navigateToDetail() {
        navController.navigateToDetail()
    }

    fun navigateToDetailAllImage() {
        navController.navigateToDetailAllImage()
    }

    fun navigateToDetailRoomsImage() {
        navController.navigateToDetailRoomsImage()
    }

    fun navigateToTour() {
        navController.navigateToTour()
    }

    private inline fun <reified T : Route> isSameCurrentDestination(): Boolean =
        navController.currentDestination?.route == T::class.qualifiedName

    @Composable
    fun showBottomBar() = MainTabType.contains {
        currentDestination?.route == it::class.qualifiedName
    }
}

@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
