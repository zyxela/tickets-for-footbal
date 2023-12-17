package com.example.football.Screens.Admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.football.Entities.Match
import com.example.football.MaskVisualTransformation
import com.example.football.Screens.Client.SearchTickets
import com.example.football.data.DatabaseHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPanel() {
    val db = DatabaseHandler()

    var isAddedMatch by remember {
        mutableStateOf(false)
    }
    var isDeleted by remember {
        mutableStateOf(false)
    }
    var isAddedStadium by remember {
        mutableStateOf(false)
    }

    var stadiums by remember {
        mutableStateOf(listOf<String>())
    }
    LaunchedEffect(Unit) {
        stadiums = SearchTickets.getStadiums()
    }

    Column(
        modifier = Modifier.width(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier.width(300.dp),
            shape = RectangleShape,
            onClick = {
                isAddedMatch = true
            }) {
            Text(text = "Добавить матч")
        }

        Button(modifier = Modifier.width(300.dp), shape = RectangleShape, onClick = {
            isDeleted = true
        }) {
            Text(text = "Убрать матч")
        }

        Button(modifier = Modifier.width(300.dp), shape = RectangleShape, onClick = {
            isAddedStadium = true
        }) {
            Text(text = "Добавить стадион")
        }

        var edit by remember {
            mutableStateOf(false)
        }
        Button(modifier = Modifier.width(300.dp), shape = RectangleShape, onClick = {
            edit = true
        }) {
            Text(text = "Редактировать матчи")
        }
        if (edit)
            EditMatches(edit)


        var participants by remember {
            mutableStateOf("")
        }
        var stadium by remember {
            mutableStateOf("")
        }
        var date by remember {
            mutableStateOf("")
        }


        var addStadiumPermission by remember {
            mutableStateOf(false)
        }
        addStadiumPermission = stadium != ""

        if (isAddedMatch) {
            var isExpanded by remember {
                mutableStateOf(false)
            }

            var stadium by remember {
                mutableStateOf("")
            }
            Dialog(onDismissRequest = {
                isAddedMatch = false
            }) {
                Card {
                    Column(modifier = Modifier.padding(6.dp)) {
                        TextField(
                            value = participants,
                            onValueChange = { participants = it },
                            label = { Text(text = "Участники") })
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
                        TextField(
                            value = date,
                            onValueChange = { date = it },
                            label = { Text(text = "Дата") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            visualTransformation = MaskVisualTransformation()
                        )
                        Button(
                            enabled = participants != "" && stadium != "" && date != "",
                            onClick = {
                                date = SearchTickets.formatString(date)
                                GlobalScope.launch {
                                    db.executeQuery("INSERT INTO matches (participants, stadium, date) VALUES ('$participants', '$stadium', '$date');")
                                    isAddedMatch = false
                                }

                            }) {

                            Text(text = "Добавить")
                        }
                    }
                }
            }
        }

        if (isDeleted) {
            Dialog(onDismissRequest = {
                isDeleted = false
            }) {
                Card {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        var matches by remember {
                            mutableStateOf<MutableList<Match>>(mutableListOf())
                        }

                        LaunchedEffect(Unit) {
                            var resultSet = db.executeQuery("SELECT * FROM matches;")
                            var m = mutableListOf<Match>()
                            resultSet?.use {
                                while (it.next()) {
                                    val id = it.getString("id").toInt()
                                    val participants = it.getString("participants")
                                    val stadium = it.getString("stadium")
                                    val date =
                                        SimpleDateFormat("yyyy-mm-dd").parse(it.getString("date"))
                                    m.add(Match(id, participants, stadium, date))
                                }
                            }
                            matches = m
                        }

                        LazyColumn(modifier = Modifier.padding(8.dp)) {

                            items(matches.count()) { i ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Card(
                                        modifier = Modifier.padding(8.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                                    ) {
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
                                            Button(
                                                onClick = {
                                                    GlobalScope.launch {
                                                        db.executeQuery("DELETE FROM matches WHERE ID = ${matches[i].id};")
                                                    }

                                                }) {
                                                Text(text = "Убрать")
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


        if (isAddedStadium) {

            Card {
                Column {
                    var stadiumName by remember {
                        mutableStateOf("")
                    }
                    TextField(value = stadiumName, onValueChange = { stadiumName = it })
                    Button(
                        //   enabled = addStadiumPermission,
                        onClick = {
                            GlobalScope.launch {
                                db.executeQuery("INSERT INTO stadium (name) VALUES ('$stadiumName');")
                            }
                            isAddedStadium = false
                        }) {
                        Text(text = "Добавить")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMatches(_isEdit: Boolean = true) {

    var isEdit by remember {
        mutableStateOf(_isEdit)
    }

    var isExpanded by remember {
        mutableStateOf(false)
    }
    val db = DatabaseHandler()
    if (isEdit) {
        Dialog(onDismissRequest = {
            isEdit = false
        }) {
            Card {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    var matches by remember {
                        mutableStateOf<MutableList<Match>>(mutableListOf())
                    }
                    var stadiums by remember {
                        mutableStateOf<MutableList<String>>(mutableListOf())
                    }

                    LaunchedEffect(Unit) {
                        var resultSet = db.executeQuery("SELECT * FROM matches;")
                        var m = mutableListOf<Match>()
                        resultSet?.use {
                            while (it.next()) {
                                val id = it.getString("id").toInt()
                                val participants = it.getString("participants")
                                val stadium = it.getString("stadium")
                                val date =
                                    SimpleDateFormat("yyyy-mm-dd").parse(it.getString("date"))
                                m.add(Match(id, participants, stadium, date))
                            }
                        }
                        matches = m

                        var rs = db.executeQuery("SELECT * FROM stadium;")
                        var s = mutableListOf<String>()
                        rs?.use {
                            while (it.next()) {
                                val stadium = it.getString("name")
                                s.add(stadium)
                            }
                        }
                        stadiums = s
                    }

                    LazyColumn(modifier = Modifier.padding(8.dp)) {

                        items(matches.count()) { i ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Card(
                                    modifier = Modifier.padding(8.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                                ) {
                                    Column(horizontalAlignment = Alignment.Start) {

                                        var p by remember {
                                            mutableStateOf(matches[i].participants)
                                        }
                                        TextField(
                                            placeholder = {
                                                Text(text = "Команды")
                                            },
                                            value = p,
                                            onValueChange = {
                                                p = it
                                            })

                                        var stadium by remember {
                                            mutableStateOf(matches[i].stadium)
                                        }
                                        ExposedDropdownMenuBox(
                                            expanded = isExpanded,
                                            onExpandedChange = { isExpanded = it }) {

                                            TextField(
                                                value = stadium,
                                                onValueChange = {},
                                                readOnly = true,
                                                trailingIcon = {
                                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                                        expanded = isExpanded
                                                    )
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


                                        Button(
                                            onClick = {
                                                GlobalScope.launch {

                                                    db.executeQuery("UPDATE matches SET participants = '$p', stadium = '$stadium'  WHERE id = ${matches[i].id};")
                                                }

                                            }) {
                                            Text(text = "Пименить")
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
}