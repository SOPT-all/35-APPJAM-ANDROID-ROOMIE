package com.wearerommies.roomie.presentation.navigator.component

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.wearerommies.roomie.presentation.navigator.MainNavigator
import com.wearerommies.roomie.presentation.navigator.route.Route
import com.wearerommies.roomie.presentation.type.MainTabType
import com.wearerommies.roomie.presentation.ui.bookmark.navigation.bookmarkNavGraph
import com.wearerommies.roomie.presentation.ui.detail.navigation.detailNavGraph
import com.wearerommies.roomie.presentation.ui.filter.navigation.filterNavGraph
import com.wearerommies.roomie.presentation.ui.home.navigation.homeNavGraph
import com.wearerommies.roomie.presentation.ui.login.navigation.loginNavGraph
import com.wearerommies.roomie.presentation.ui.map.navigation.mapNavGraph
import com.wearerommies.roomie.presentation.ui.mood.navigation.moodNavGraph
import com.wearerommies.roomie.presentation.ui.mypage.navigation.myNavGraph
import com.wearerommies.roomie.presentation.ui.search.navigation.searchNavGraph
import com.wearerommies.roomie.presentation.ui.splash.navigation.splashNavGraph
import com.wearerommies.roomie.presentation.ui.tour.navigation.tourNavGraph
import com.wearerommies.roomie.presentation.ui.webview.navigation.webViewNavGraph

@Composable
fun RoomieNavHost(
    navigator: MainNavigator,
    padding: PaddingValues,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        splashNavGraph()
        loginNavGraph(
            paddingValues = padding,
            navigateUp = navigator::popBackStackIfNotHome,
            navigateToHome = {
                val navOptions = navOptions {
                    popUpTo<Route.Login> {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
                navigator.navigateToHome()
            }
        )
        homeNavGraph(
            paddingValues = padding,
            navigateToBookmark = navigator::navigateToBookmark,
            navigateToMood = navigator::navigateToMood,
            navigateToMap = { navigator.navigate(tab = MainTabType.MAP) },
            navigateToDetail = navigator::navigateToDetail,
            navigateToWebView = navigator::navigateToWebView
        )
        mapNavGraph(
            paddingValues = padding,
            navigateToSearch = navigator::navigateToSearch,
            navigateToFilter = navigator::navigateToFilter,
            navigateToDetail = navigator::navigateToDetail
        )
        myNavGraph(
            paddingValues = padding,
            navigateToBookmark = navigator::navigateToBookmark
        )
        searchNavGraph(
            paddingValues = padding,
            navigateUp = navigator::popBackStackIfNotHome,
            navigateToMap = navigator::navigateToMap
        )
        moodNavGraph(
            paddingValues = padding,
            navigateUp = navigator::popBackStackIfNotHome,
            navigateToDetail = navigator::navigateToDetail
        )
        bookmarkNavGraph(
            paddingValues = padding,
            navigateUp = navigator::popBackStackIfNotHome,
            navigateToDetail = navigator::navigateToDetail
        )
        filterNavGraph(
            paddingValues = padding,
            navigateUp = navigator::popBackStackIfNotHome,
            navigateToMap = navigator::navigateToMap
        )
        detailNavGraph(
            paddingValues = padding,
            navigateUp = navigator::popBackStackIfNotHome,
            navigateDetailRoom = navigator::navigateToDetailRoom,
            navigateDetailHouse = navigator::navigateToDetailHouse,
            navigateTourApply = navigator::navigateToTourFirstStep,
            navigateToWebView = navigator::navigateToWebView
        )
        tourNavGraph(
            paddingValues = padding,
            navigateUp = navigator::popBackStackIfNotHome,
            navigateToSecondStep = navigator::navigateToTourSecondStep,
            navigateToThirdStep = navigator::navigateToTourThirdStep,
            navigateToCompleteStep = navigator::navigateToCompleteStep,
            navigateToHome = navigator::navigateToHome
        )
        webViewNavGraph(
            paddingValues = padding,
            navigateUp = navigator::popBackStackIfNotHome
        )
    }
}