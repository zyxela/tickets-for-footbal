package com.example.football.Screens.Entier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.football.Navigation.Screen

@Composable
fun MeetingScreen(navHostController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                modifier = Modifier.width(175.dp),
                onClick = {
                    navHostController.navigate(Screen.Authorization.route)
                }
            ) {
                Text(text = "Войти")
            }

            OutlinedButton(
                modifier = Modifier.width(175.dp),
                onClick = {
                    navHostController.navigate(Screen.Registration.route)
                }
            ) {
                Text(text = "Зарегистрироваться")
            }
        }
    }
}