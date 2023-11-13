package com.example.football.Screens.Admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun AdminPanel() {
    Column(
        modifier = Modifier.width(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier.width(300.dp),
            shape = RectangleShape,
            onClick = {

            }) {
            Text(text = "Добавить матч")
        }

        Button(modifier = Modifier.width(300.dp), shape = RectangleShape, onClick = {

        }) {
            Text(text = "Убрать матч")
        }

        Button(modifier = Modifier.width(300.dp), shape = RectangleShape, onClick = {

        }) {
            Text(text = "Добавить стадион")
        }
    }
}