package com.example.football


import com.example.football.Entities.Match
import com.example.football.data.DatabaseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

object Search {
    suspend fun search():List<Match> = withContext(Dispatchers.IO){
        val matchList = mutableListOf<Match>()
        val db = DatabaseHandler()
        launch {
            val res = db.executeQuery("SELECT * FROM matches;")
            res?.use {
                while (it.next()){
                    val match = Match(
                        it.getString("id").toInt(),
                        it.getString("participants"),
                        it.getString("stadium"),
                        SimpleDateFormat("yyyy-mm-dd").parse( it.getString("date")),
                    )
                    matchList.add(match)
                }
            }
        }

        return@withContext matchList
    }
    suspend fun search(stadium:String):List<Match> = withContext(Dispatchers.IO){
        val matchList = mutableListOf<Match>()
        val db = DatabaseHandler()
        launch {
            val res = db.executeQuery("SELECT * FROM matches WHERE stadium = '$stadium';")
            res?.use {
                while (it.next()){
                    val match = Match(
                        it.getString("id").toInt(),
                        it.getString("participants"),
                        it.getString("stadium"),
                        SimpleDateFormat("yyyy-mm-dd").parse( it.getString("date")),
                    )
                    matchList.add(match)
                }
            }
        }

        return@withContext matchList
    }

    suspend fun search(stadium:String, dateFrom:String, dateTo:String):List<Match> = withContext(Dispatchers.IO){
        val matchList = mutableListOf<Match>()
        val db = DatabaseHandler()
        launch {
            val res = db.executeQuery("SELECT * FROM matches WHERE stadium = '$stadium' AND date BETWEEN '$dateFrom' AND '$dateTo';")
            res?.use {
                while (it.next()){
                    val match = Match(
                        it.getString("id").toInt(),
                        it.getString("participants"),
                        it.getString("stadium"),
                        SimpleDateFormat("yyyy-mm-dd").parse( it.getString("date")),
                    )
                    matchList.add(match)
                }
            }
        }

        return@withContext matchList
    }


}