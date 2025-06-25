package com.wearerommies.roomie.presentation.ui.mypage.nickname.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.wearerommies.roomie.presentation.navigator.route.Route
import com.wearerommies.roomie.presentation.ui.mypage.nickname.NicknameRoute

fun NavController.navigateToNickname(nickname: String, navOptions: NavOptions? = null) {
    navigate(
        route = Route.Nickname(
            nickname = nickname
        ),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.nicknameNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    ) {
    composable<Route.Nickname> { backStackEntry ->
        val nickname = backStackEntry.toRoute<Route.Nickname>().nickname
        NicknameRoute(
            paddingValues = paddingValues,
            nickname = nickname,
            navigateUp = navigateUp,
        )
    }
}