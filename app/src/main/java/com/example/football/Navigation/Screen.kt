package com.example.football.Navigation

sealed class Screen(var route:String){
    object Authorization:Screen(route = "auth")
    object Registration:Screen(route = "reg")
    object ConfirmEmail:Screen(route = "auth")
}
