package com.rodriguez.smartfitv2.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rodriguez.smartfitv2.ui.catalog.CatalogScreen
import com.rodriguez.smartfitv2.ui.favorites.FavoritesScreen
import com.rodriguez.smartfitv2.ui.home.HomeScreen
import com.rodriguez.smartfitv2.ui.login.LoginScreen
import com.rodriguez.smartfitv2.ui.measurement.MeasurementHistoryScreen
import com.rodriguez.smartfitv2.ui.profile.ProfileScreen
import com.rodriguez.smartfitv2.ui.qr.QrScannerScreen
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
            HomeScreen(navController) // Llama a la función Composable directamente
        }

        composable("measurement_history") {
            MeasurementHistoryScreen(navController)
        }

        composable("catalog") {
            CatalogScreen(navController)
        }

        composable("qrscanner") {
            QrScannerScreen(navController) { scannedText ->
                // TODO: Parsear medidas y guardarlas en Room
                Log.d("QR_RESULT", scannedText) // Puedes hacerlo más adelante
            }

        }

        composable("profile") {
            ProfileScreen(navController)
        }


        composable("favorites") {
            FavoritesScreen(navController)
        }


    }
}