package com.example.football.data

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DatabaseHandler {

    private var connection: Connection? = null

    private val host = "10.0.2.2"

    private val database = "test"
    private val port = 5432
    private val user = "postgres"
    private val pass = ""
    private var url = "jdbc:postgresql://%s:%d/%s"
    private var status = false

    fun Database() {
        url = String.format(url, host, port, database)
        connect()
        //this.disconnect();
        Log.i("SQL", "connection status:$status")
    }

    private fun connect() {
        val thread = Thread {
            try {
                Class.forName("org.postgresql.Driver")
                connection = DriverManager.getConnection(url, user, pass)
                status = true
                Log.i("SQL", "connection status:$status")
            } catch (e: Exception) {
                status = false
                Log.e("SQL", e.message.toString())
            }
        }
        thread.start()
        try {
            thread.join()
        } catch (e: Exception) {
            Log.e("connect", e.message.toString())
            status = false
        }
    }


    suspend fun executeQuery(query: String): ResultSet? = suspendCoroutine { continuation ->
        val rs = null
        url = String.format(url, host, port, database)
        GlobalScope.launch {
            try{
                Class.forName("org.postgresql.Driver")
                connection = DriverManager.getConnection(url, user, pass)
                val statement = connection?.createStatement()
                continuation.resume(statement?.executeQuery(query))
            }catch (e:Exception){
                continuation.resume(rs)
            }

        }

    }


}