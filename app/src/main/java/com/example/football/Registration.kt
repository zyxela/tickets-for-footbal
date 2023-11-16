package com.example.football

import com.example.football.data.DatabaseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object Registration {
    private suspend fun checkUser(login: String): Boolean = withContext(Dispatchers.IO) {
        val db = DatabaseHandler()

        val response = db.executeQuery("SELECT * FROM users WHERE name = '$login';")
        response?.use { it->
            while (it.next()) {
                if (it.getString("name") == login)
                    return@withContext true
            }
        }
        return@withContext false

    }


    suspend fun regist(login: String, password: String): Boolean = withContext(Dispatchers.IO) {

        val db = DatabaseHandler()
        var check = false
        launch {
            check = checkUser(login)
        }.join()

        if (!check) {
            db.executeQuery("INSERT INTO users (name, password) VALUES ('$login', '$password');")
        }
        return@withContext (check)


    }
}