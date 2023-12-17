package com.example.football.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.football.Screens.Admin.AdminPanel
import com.example.football.Screens.Client.Matches
import com.example.football.Screens.Client.SearchTicket
import com.example.football.Screens.Entier.Authorization
import com.example.football.Screens.Entier.MeetingScreen
import com.example.football.Screens.Entier.Registration

@Composable
fun ScreenGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Meeting.route) {
        composable(Screen.Authorization.route) {
            Authorization(navHostController = navController)
        }

        composable(Screen.Registration.route) {
            Registration(navHostController = navController)
        }

        composable(Screen.Meeting.route) {
            MeetingScreen(navHostController = navController)
        }

        composable(Screen.SearchTicket.route) {
            SearchTicket(navHostController = navController)
        }
        composable(
            "matches/{stadium}/{dateFrom}/{dateTo}/{p}",
            arguments = listOf(
                navArgument("p") {
                    defaultValue = ""
                    type = NavType.StringType
                },
                navArgument("stadium") {
                    defaultValue = ""
                    type = NavType.StringType
                },
                navArgument("dateFrom") {
                    defaultValue = ""
                    type = NavType.StringType
                },
                navArgument("dateTo") {
                    defaultValue = ""
                    type = NavType.StringType
                }
            )
        ) { bse ->
            val p = bse.arguments?.getString("p")!!
            val s = bse.arguments?.getString("stadium")!!
            val df = bse.arguments?.getString("dateFrom")!!
            val dt = bse.arguments?.getString("dateTo")!!
            Matches(
                navHostController = navController, s, df, dt, p
            )

        }
        composable(Screen.AdminPanel.route) {
            AdminPanel()
        }
    }
}