package com.wearerommies.roomie.presentation.ui.mypage.myaccount.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.wearerommies.roomie.presentation.navigator.route.Route
import com.wearerommies.roomie.presentation.ui.mypage.myaccount.MyAccountRoute

fun NavController.navigateToMyAccount(navOptions: NavOptions? = null) {
    navigate(
        route = Route.MyAccount,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.myAccountNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateToName: (String) -> Unit,
    navigateToNickname: (String) -> Unit,
    navigateToBirth: (String) -> Unit,
    navigateToGender: (String) -> Unit,
    navigateToContact: (String) -> Unit,
    navigateToOnboarding: () -> Unit,
) {
    composable<Route.MyAccount> {
        MyAccountRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateToName = navigateToName,
            navigateToNickname = navigateToNickname,
            navigateToBirth = navigateToBirth,
            navigateToGender = navigateToGender,
            navigateToContact = navigateToContact,
            navigateToOnboarding = navigateToOnboarding
        )
    }
}
