package com.example.football.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.football.Screens.Admin.AdminPanel
import com.example.football.Screens.Entier.Authorization
import com.example.football.Screens.Entier.ConfirmEmail
import com.example.football.Screens.Entier.MeetingScreen
import com.example.football.Screens.Entier.Registration
import com.example.football.Screens.Client.Matches
import com.example.football.Screens.Client.SearchTicket

@Composable
fun ScreenGraph(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.AdminPanel.route) {
        composable(Screen.Authorization.route){
            Authorization(navHostController = navController)
        }

        composable(Screen.ConfirmEmail.route){
            ConfirmEmail(navHostController = navController)
        }

        composable(Screen.Registration.route){
            Registration(navHostController = navController)
        }

        composable(Screen.Meeting.route){
            MeetingScreen(navHostController = navController)
        }

        composable(Screen.SearchTicket.route){
            SearchTicket(navHostController = navController)
        }
        composable(Screen.Matches.route){
            Matches(navHostController = navController)
        }
        composable(Screen.AdminPanel.route){
            AdminPanel()
        }
    }
}