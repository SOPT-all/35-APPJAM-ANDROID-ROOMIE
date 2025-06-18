package com.wearerommies.roomie.presentation.ui.mypage.contact.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.wearerommies.roomie.presentation.navigator.route.Route
import com.wearerommies.roomie.presentation.ui.mypage.contact.ContactRoute

fun NavController.navigateToContact(contact: String, navOptions: NavOptions? = null) {
    navigate(
        route = Route.Contact(
            contact = contact
        ),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.contactNavGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
) {
    composable<Route.Contact> { backStackEntry ->
        val contact = backStackEntry.toRoute<Route.Contact>().contact
        ContactRoute(
            paddingValues = paddingValues,
            contact = contact,
            navigateUp = navigateUp
        )
    }
}