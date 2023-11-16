package com.example.football.Entities

import java.util.Date
data class Match(
    val id: Int,
    val participants: String,
    val stadium: String,
    val date: Date,
)