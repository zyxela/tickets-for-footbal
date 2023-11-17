package com.example.football.Navigation

sealed class Screen(var route: String) {
    object Authorization : Screen(route = "auth")
    object Registration : Screen(route = "reg")
    object Meeting : Screen(route = "meet")
    object SearchTicket : Screen(route = "search_tickets")
    object Matches : Screen(route = "matches/{stadium}/{dateFrom}/{dateTo}")
    object AdminPanel : Screen(route = "admin_panel")
}
