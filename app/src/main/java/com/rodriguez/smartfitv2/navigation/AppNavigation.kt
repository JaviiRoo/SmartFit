package com.rodriguez.smartfitv2.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rodriguez.smartfitv2.data.repository.ProfileRepository
import com.rodriguez.smartfitv2.ui.avatar.AvatarConfigScreen
import com.rodriguez.smartfitv2.ui.catalog.CatalogScreen
import com.rodriguez.smartfitv2.ui.favorites.FavoritesScreen
import com.rodriguez.smartfitv2.ui.home.HomeScreen
import com.rodriguez.smartfitv2.ui.login.LoginScreen
import com.rodriguez.smartfitv2.ui.measurement.MeasurementHistoryScreen
import com.rodriguez.smartfitv2.ui.profile.CreateProfileScreen
import com.rodriguez.smartfitv2.ui.profile.ProfileScreen
import com.rodriguez.smartfitv2.ui.profile.ProfileSelectorScreen
import com.rodriguez.smartfitv2.ui.qr.QrScannerScreen
import com.rodriguez.smartfitv2.ui.register.RegisterScreen
import com.rodriguez.smartfitv2.ui.splash.SplashScreen         // ← importar el splash
import com.rodriguez.smartfitv2.viewmodel.ProfileFactoryViewModel
import com.rodriguez.smartfitv2.viewmodel.ProfileViewModel

@Composable
fun AppNavigation(navController: NavHostController, profileRepository: ProfileRepository) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH              // ← ahora arranca en splash
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }
        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }
        composable(Routes.REGISTER) {
            RegisterScreen(navController)
        }
        composable(Routes.HOME) {
            val profileViewModel: ProfileViewModel = viewModel(
                factory = ProfileFactoryViewModel(profileRepository)
            )
            HomeScreen(navController, profileViewModel)
        }
        composable(Routes.MEASUREMENT_HISTORY) {
            MeasurementHistoryScreen(navController)
        }
        composable(Routes.CATALOG) {
            CatalogScreen(navController)
        }
        composable(Routes.QRSCANNER) {
            QrScannerScreen(navController) { scannedText ->
                Log.d("QR_RESULT", scannedText)
            }
        }
        composable(Routes.PROFILE) {
            ProfileScreen(navController)
        }
        composable(Routes.AVATAR_CONFIG) {
            val profileViewModel: ProfileViewModel = viewModel(
                factory = ProfileFactoryViewModel(profileRepository)
            )
            AvatarConfigScreen(navController, profileViewModel)
        }
        composable(Routes.FAVORITES) {
            FavoritesScreen(navController)
        }
        composable(Routes.PROFILE_SELECTOR) {
            val profileViewModel: ProfileViewModel = viewModel(
                factory = ProfileFactoryViewModel(profileRepository)
            )
            ProfileSelectorScreen(navController, profileRepository, profileViewModel)
        }
        composable(Routes.CREATE_PROFILE) {
            CreateProfileScreen(navController, profileRepository, profileId = null)
        }
        composable(
            Routes.CREATE_PROFILE_WITH_ID,
            arguments = listOf(navArgument("profileId") { type = NavType.IntType })
        ) { backStackEntry ->
            val profileId = backStackEntry.arguments?.getInt("profileId")
            CreateProfileScreen(navController, profileRepository, profileId)
        }
    }
}
