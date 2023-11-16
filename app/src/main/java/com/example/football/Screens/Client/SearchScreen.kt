package com.example.football.Screens.Client

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.football.Entities.Match
import com.example.football.Entities.MyTicket
import com.example.football.Entities.User
import com.example.football.MyTickets
import com.example.football.Navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTicket(navHostController: NavHostController) {

    var isDialogShowed by remember {
        mutableStateOf(false)
    }

    Row(horizontalArrangement = Arrangement.End, modifier = Modifier.padding(12.dp)) {
        Icon(
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    isDialogShowed = true
                },
            imageVector = Icons.Outlined.Article,
            contentDescription = "",
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Card(
            modifier = Modifier
                .width(350.dp)
                .padding(8.dp),

            ) {
            Column(
                modifier = Modifier
                    .width(350.dp)
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                var isExpanded by remember {
                    mutableStateOf(false)
                }

                var stadium by remember {
                    mutableStateOf("")
                }
                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { isExpanded = it }) {

                    TextField(
                        value = stadium,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                        },
                        placeholder = {
                            Text(text = "Выберите стадион")
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = {
                            isExpanded = false
                        }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = "Динамо")
                            },
                            onClick = {
                                stadium = "Динамо"
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = "Минск Арена")
                            },
                            onClick = {
                                stadium = "Минск Арена"
                                isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = "Динамо-Арена")
                            },
                            onClick = {
                                stadium = "Динамо-Арена"
                                isExpanded = false
                            }
                        )
                    }


                }


                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("C")
                    var dateFrom by remember {
                        mutableStateOf("")
                    }
                    TextField(
                        modifier = Modifier.width(90.dp),
                        value = dateFrom,
                        onValueChange = { dateFrom = it })

                    Text("До")
                    var dateTo by remember {
                        mutableStateOf("")
                    }
                    TextField(
                        modifier = Modifier.width(90.dp),
                        value = dateFrom,
                        onValueChange = { dateTo = it })
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    onClick = {
                        navHostController.navigate(Screen.Matches.route)
                    }) {
                    Text("Выбрать матчи")
                }
            }

        }

    }


    if (isDialogShowed) {
        Dialog(onDismissRequest = {
            isDialogShowed = false
        }) {
            Card {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    
                    var matches = listOf<Match>()

                    LaunchedEffect(Unit){
                        matches = MyTickets.getMyTickets(MyTicket(27,29,27 ))
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
                                            fontWeight = FontWeight(weight = 400),
                                            text = matches[i].date.toString()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}