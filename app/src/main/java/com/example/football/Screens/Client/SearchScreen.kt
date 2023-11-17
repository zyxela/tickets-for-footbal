package com.example.football.Screens.Client

import android.content.Context
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.football.Entities.Match
import com.example.football.MaskVisualTransformation
import com.example.football.MyTickets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTicket(navHostController: NavHostController) {

    var isDialogShowed by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    var stadiums by remember {
        mutableStateOf(listOf<String>())
    }
    LaunchedEffect(Unit) {
        stadiums = SearchTickets.getStadiums()
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
                        stadiums.forEach { s ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = s)
                                },
                                onClick = {
                                    stadium = s
                                    isExpanded = false
                                }
                            )
                        }

                    }


                }

                var dateFrom by remember {
                    mutableStateOf("")
                }
                var dateTo by remember {
                    mutableStateOf("")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("C")

                    TextField(
                        modifier = Modifier.width(130.dp),
                        value = dateFrom,
                        onValueChange = { dateFrom = it },
                        visualTransformation = MaskVisualTransformation()
                    )

                    Text("До")

                    TextField(
                        modifier = Modifier.width(130.dp),
                        value = dateTo,
                        onValueChange = { dateTo = it },
                        visualTransformation = MaskVisualTransformation()
                    )
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    onClick = {
                        navHostController.navigate(
                            "matches/{stadium}/{dateFrom}/{dateTo}"
                                .replace("{stadium}", if (stadium != "") stadium else "{stadium}")
                                .replace(
                                    "{dateFrom}",
                                    if (dateFrom != "") dateFrom else "{dateFrom}"
                                )
                                .replace("{dateTo}", if (dateTo != "") dateTo else "{dateTo}")

                        )
                    }) {
                    Text("Выбрать матчи")
                }
            }

        }

    }

    val sp = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

    if (isDialogShowed) {
        Dialog(onDismissRequest = {
            isDialogShowed = false
        }) {
            Card {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    var matches by remember {
                        mutableStateOf(listOf<Match>())
                    }


                    LaunchedEffect(Unit) {
                        val userId = sp.getInt("USER_ID", 0)
                        matches = MyTickets.getMyTickets(userId)
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