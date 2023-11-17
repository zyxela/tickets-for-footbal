package com.example.football

import com.example.football.Entities.Match
import com.example.football.data.DatabaseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

object MyTickets {

    suspend fun getMyTickets(usersMatchesId: Int): List<Match> = withContext(Dispatchers.IO) {
        val db = DatabaseHandler()
        val matchesList = mutableListOf<Match>()


        launch {
            val res =
                db.executeQuery("SELECT * FROM matches INNER JOIN usersmatches ON matches.id = usersMatches.match_id WHERE usersmatches.user_id = $usersMatchesId;")
            res?.use {
                while (it.next()) {
                    val match = Match(
                        it.getString("id").toInt(),
                        it.getString("participants"),
                        it.getString("stadium"),
                        SimpleDateFormat("yyyy-mm-dd").parse(it.getString("date"))
                    )
                    matchesList.add(match)
                }
            }
        }.join()
        return@withContext matchesList.toList()
    }

    suspend fun buyTicket(matchId: Int, userId: Int) = withContext(Dispatchers.IO) {
        val db = DatabaseHandler()

        launch {
            db.executeQuery("INSERT INTO usersmatches (match_id, user_id) VALUES ($matchId, $userId);")
        }.join()
    }

}


