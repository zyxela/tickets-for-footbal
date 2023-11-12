package com.example.football.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.football.Screens.Authorization
import com.example.football.Screens.ConfirmEmail
import com.example.football.Screens.Registration

@Composable
fun ScreenGraph(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Authorization.route) {
        composable(Screen.Authorization.route){
            Authorization(navHostController = navController)
        }

        composable(Screen.ConfirmEmail.route){
            ConfirmEmail(navHostController = navController)
        }

        composable(Screen.Registration.route){
            Registration(navHostController = navController)
        }
    }
}