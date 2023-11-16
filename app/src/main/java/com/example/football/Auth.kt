package com.example.football

import com.example.football.data.DatabaseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object Auth {
     suspend fun checkUser(login: String, password:String): Boolean = withContext(Dispatchers.IO) {
        val db = DatabaseHandler()

        val response = db.executeQuery("SELECT * FROM users WHERE name = '$login' AND password = '$password';")
        response?.use {it->
            while (it.next()){
                if (it.getString("name") == login && it.getString("password") == password)
                    return@withContext true
            }
        }
        return@withContext false
    }
}