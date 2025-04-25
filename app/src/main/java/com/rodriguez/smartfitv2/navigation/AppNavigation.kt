package com.rodriguez.smartfitv2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rodriguez.smartfitv2.ui.home.HomeScreenContent // Importa HomeScreenContent
import com.rodriguez.smartfitv2.ui.login.LoginScreen
import com.rodriguez.smartfitv2.ui.register.RegisterScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("home") {
            HomeScreenContent() // Llama a la función Composable directamente
        }
    }
}