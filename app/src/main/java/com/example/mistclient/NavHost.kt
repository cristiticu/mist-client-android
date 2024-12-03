package com.example.mistclient

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mistclient.auth.ui.LoginScreen
import com.example.mistclient.games.ui.StoreGamesListScreen

@Composable
fun MistClientNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "/login") {
        composable("/login") {
            LoginScreen(onClose = { navController.navigate("/games") })
        }
        composable("/games") {
            StoreGamesListScreen(onGameClick = { navController.navigate("/games/$it") })
        }
        composable(
            route = "/games/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            val gameId = it.arguments?.getString("id")

            if (gameId == null) {
                navController.popBackStack()
            }
            Text("Game details $gameId")
        }
    }
}