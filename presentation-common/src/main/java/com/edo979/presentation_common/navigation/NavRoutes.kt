package com.edo979.presentation_common.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

private const val ROUTE_POSTS = "posts"
private const val ROUTE_POST = "posts/%s"
private const val ARG_POST_ID = "postId"

sealed class NavRoutes(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {

    data object Posts : NavRoutes(ROUTE_POSTS)

    data object Post : NavRoutes(
        route = String.format(ROUTE_POST, "{$ARG_POST_ID}"),
        arguments = listOf(navArgument(ARG_POST_ID) { type = NavType.LongType })
    ) {
        fun routeForPost(postArg: PostRouteArg) =
            String.format(ROUTE_POST, postArg.id)

        fun fromEntry(entry: NavBackStackEntry) =
            PostRouteArg(entry.arguments?.getLong(ARG_POST_ID) ?: 0L)
    }
}