package com.example.football.Screens.Client

import com.example.football.data.DatabaseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object SearchTickets {
    suspend fun getStadiums(): List<String> = withContext(Dispatchers.IO) {
        val stadiums = mutableListOf<String>()

        launch {
            val res = DatabaseHandler().executeQuery("SELECT * FROM stadium;")
            res?.use {
                while (it.next()) {
                    stadiums.add(it.getString("name"))
                }
            }
        }.join()


        return@withContext stadiums
    }
}