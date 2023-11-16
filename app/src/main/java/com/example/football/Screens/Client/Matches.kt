package com.example.football.Screens.Client

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.football.Entities.Match
import com.example.football.Navigation.Screen
import com.example.football.Search

@Composable
fun Matches(navHostController: NavHostController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var matches by remember {
            mutableStateOf<List<Match>>(mutableListOf())
        }
        LaunchedEffect(Unit) {
            matches = Search.search()
        }

        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(matches.count()) { i ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Card(modifier = Modifier.padding(8.dp)) {
                        Column(horizontalAlignment = Alignment.Start) {

                            Text(
                                fontSize = 22.sp,
                                fontWeight = FontWeight(500),
                                text = matches[i].participants
                            )
                            Text(
                                fontSize = 18.sp,
                                fontWeight = FontWeight(450),
                                text = matches[i].stadium
                            )
                            Text(
                                fontSize = 14.sp,
                                fontWeight = FontWeight(400),
                                text = matches[i].date.toString()
                            )
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    navHostController.navigate(Screen.SearchTicket.route)
                                }) {
                                Text(text = "Купить билет")
                            }
                        }
                    }
                }
            }
        }
    }
}