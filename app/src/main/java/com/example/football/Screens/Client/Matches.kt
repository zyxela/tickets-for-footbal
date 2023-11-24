package com.example.football.Screens.Client

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.football.Entities.Match
import com.example.football.MyTickets
import com.example.football.Navigation.Screen
import com.example.football.Search
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun Matches(
    navHostController: NavHostController,
    stadium: String = "",
    dateFrom: String = "",
    dateTo: String = "",
) {

    val context = LocalContext.current
    val userId = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE).getInt("USER_ID", 0)


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        var matches by remember {
            mutableStateOf<List<Match>>(mutableListOf())
        }
        LaunchedEffect(Unit) {
            matches =
                if (stadium != "{stadium}" || dateFrom != "{dateFrom}") {
                    if (stadium != "{stadium}" && dateFrom != "{dateFrom}") {
                        Search.search(stadium, dateFrom, dateTo)
                    } else if (stadium != "{stadium}") {
                        Search.search(stadium)
                    } else {
                        Search.search(dateFrom, dateTo)
                    }


                } else
                    Search.search()

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
                                    GlobalScope.launch { MyTickets.buyTicket(matches[i].id, userId) }
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