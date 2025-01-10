package com.example.xptracker.navigation

sealed class AppScene (val route: String) {
    object Lista: AppScene("Lista")
    object Horas: AppScene("Horas")
}