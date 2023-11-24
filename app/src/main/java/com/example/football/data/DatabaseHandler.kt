package com.example.football.data

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DatabaseHandler {

    private var connection: Connection? = null

    private val host = "192.168.137.1"

    private val database = "test"
    private val port = 5432
    private val user = "postgres"
    private val pass = ""
    private var url = "jdbc:postgresql://%s:%d/%s"


    suspend fun executeQuery(query: String): ResultSet? = suspendCoroutine { continuation ->
        val rs = null
        url = String.format(url, host, port, database)
        GlobalScope.launch {
            try {
                Class.forName("org.postgresql.Driver")
                connection = DriverManager.getConnection(url, user, pass)
                val statement = connection?.createStatement()
                continuation.resume(statement?.executeQuery(query))
            } catch (e: Exception) {
                continuation.resume(rs)
            }

        }

    }


}