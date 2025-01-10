package com.example.xptracker.navigation

import Lista
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.xptracker.Horas

@Composable
fun AppNavigation() {
    val navControlador = rememberNavController()
    NavHost(navController = navControlador, startDestination = AppScene.Lista.route) {
        composable(AppScene.Lista.route) {
           Lista(navControlador)
        }
        composable(AppScene.Horas.route) {
            Horas(navControlador)
        }
    }
}