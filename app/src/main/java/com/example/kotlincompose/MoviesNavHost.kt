/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.kotlincompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kotlincompose.model.MovieResult
import com.example.kotlincompose.ui.theme.MoviesDetailsScreen
import com.example.kotlincompose.ui.theme.moviesdetails.MoviesDetailsScreen
import com.example.kotlincompose.ui.theme.movieslist.MoviesListScreen
import com.google.gson.Gson


@Composable
fun MoviesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = com.example.kotlincompose.ui.theme.MoviesListScreen.route,
        modifier = modifier
    ) {
        composable(route = com.example.kotlincompose.ui.theme.MoviesListScreen.route) {
            MoviesListScreen { movDetails ->
                navController.navigateToMoviesDetailsScreen(Gson().toJson(movDetails))
            }
        }
        composable(
            route = MoviesDetailsScreen.routeWithArgs,
            arguments = MoviesDetailsScreen.arguments
        ) { navBackStackEntry ->

//            val movie =
//                navBackStackEntry.arguments?.getParcelable<MovieResult>(MoviesDetailsScreen.detailScreenArg)
                        val movie =
                navBackStackEntry.arguments?.getString(MoviesDetailsScreen.detailScreenArg)
            val movieObject = Gson().fromJson(movie, MovieResult::class.java)
            MoviesDetailsScreen(movieObject)
        }

    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

private fun NavHostController.navigateToMoviesDetailsScreen(movDetails: String) {
    this.navigateSingleTopTo("${MoviesDetailsScreen.route}/$movDetails")
}
