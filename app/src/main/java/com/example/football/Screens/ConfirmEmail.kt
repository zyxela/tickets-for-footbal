package com.example.football.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmEmail(navHostController: NavHostController){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            val message = remember { mutableStateOf("") }

            OutlinedTextField(
                message.value,
                { message.value = it },
                textStyle = TextStyle(fontSize = 30.sp),
                keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Number),
                label = {
                    Text(
                        text = "Code",
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    )
                }
            )
            Button(modifier = Modifier.width(88.dp), onClick = { }) {
                Text(
                    text = "Отправить",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth(3f),
                    textAlign = TextAlign.Center
                )

            }

        }
    }
}