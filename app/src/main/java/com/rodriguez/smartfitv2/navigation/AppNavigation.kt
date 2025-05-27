package com.rodriguez.smartfitv2.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rodriguez.smartfitv2.data.dao.AvatarPartDao
import com.rodriguez.smartfitv2.data.repository.ClothingRepository
import com.rodriguez.smartfitv2.data.repository.ProfileRepository
import com.rodriguez.smartfitv2.ui.admin.AdminDashboardScreen
import com.rodriguez.smartfitv2.ui.avatar.AvatarConfigScreen
import com.rodriguez.smartfitv2.ui.avatar.SelectPartScreen
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
import com.rodriguez.smartfitv2.ui.splash.SplashScreen
import com.rodriguez.smartfitv2.viewmodel.ProfileFactoryViewModel
import com.rodriguez.smartfitv2.viewmodel.ProfileViewModel
<<<<<<< HEAD
import com.rodriguez.smartfitv2.viewmodel.AvatarConfigViewModel
import com.rodriguez.smartfitv2.viewmodel.AvatarConfigViewModelFactory
=======
import com.rodriguez.smartfitv2.ui.dashboard.AdminUserFormScreen
import com.rodriguez.smartfitv2.ui.dashboard.AdminUsersListScreen
>>>>>>> origin/main

@Composable
fun AppNavigation(
    navController: NavHostController,
    profileRepository: ProfileRepository,
    clothingRepository: ClothingRepository,
    avatarPartDao: AvatarPartDao
) {

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) { SplashScreen(navController) }
        composable(Routes.LOGIN) { LoginScreen(navController) }
        composable(Routes.REGISTER) { RegisterScreen(navController) }

        composable(
            route = Routes.HOME_WITH_ARG,
            arguments = listOf(navArgument("profileId") { type = NavType.IntType })
        ) { backStackEntry ->
<<<<<<< HEAD
            val profileViewModel: ProfileViewModel = viewModel(
                factory = ProfileFactoryViewModel(profileRepository, clothingRepository)
            )
            val avatarConfigViewModel: AvatarConfigViewModel = viewModel(
                factory = AvatarConfigViewModelFactory(avatarPartDao)
            )

            val profileId = backStackEntry.arguments?.getInt("profileId") ?: 0
            HomeScreen(
                navController = navController,
                profileViewModel = profileViewModel,
                avatarConfigViewModel = avatarConfigViewModel,
                selectedProfileId = profileId
            )
        }
        composable(Routes.MEASUREMENT_HISTORY) {
            MeasurementHistoryScreen(navController)
        }
        // --- Ruta de catálogo/escaparate con argumento de búsqueda ---
        composable(
            route = "catalog_screen/{searchQuery}/{userWaist}/{userChest}/{userHip}/{userLeg}",
            arguments = listOf(
                navArgument("searchQuery") { type = NavType.StringType },
                navArgument("userWaist") { type = NavType.StringType },
                navArgument("userChest") { type = NavType.StringType },
                navArgument("userHip") { type = NavType.StringType },
                navArgument("userLeg") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val query = backStackEntry.arguments?.getString("searchQuery") ?: ""
            fun parseArg(arg: String?): Int? = arg?.toIntOrNull()?.takeIf { it != -1 }
            val waist = parseArg(backStackEntry.arguments?.getString("userWaist"))
            val chest = parseArg(backStackEntry.arguments?.getString("userChest"))
            val hip = parseArg(backStackEntry.arguments?.getString("userHip"))
            val leg = parseArg(backStackEntry.arguments?.getString("userLeg"))
            CatalogScreen(navController, query, waist, chest, hip, leg, clothingRepository)
        }

=======
            val profileViewModel: ProfileViewModel = viewModel(factory = ProfileFactoryViewModel(profileRepository))
            val profileId = backStackEntry.arguments?.getInt("profileId") ?: 0
            HomeScreen(navController, profileViewModel, profileId)
        }

        composable(Routes.MEASUREMENT_HISTORY) { MeasurementHistoryScreen(navController) }
        composable(Routes.CATALOG) { CatalogScreen(navController) }
>>>>>>> origin/main
        composable(Routes.QRSCANNER) {
            QrScannerScreen(navController) { scannedText ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("parte_actualizada", scannedText)
                navController.popBackStack()
            }
        }

        composable(
            route = "select_part_screen/{medida}",
            arguments = listOf(navArgument("medida") { type = NavType.StringType })
        ) { backStackEntry ->
            val medida = backStackEntry.arguments?.getString("medida") ?: ""
            SelectPartScreen(navController, medida)
        }

        composable(Routes.PROFILE) { ProfileScreen(navController) }

        composable(
            route = Routes.AVATAR_CONFIG_WITH_ARG,
            arguments = listOf(navArgument("profileId") { type = NavType.IntType })
        ) { backStackEntry ->
<<<<<<< HEAD
            val profileViewModel: ProfileViewModel = viewModel(
                factory = ProfileFactoryViewModel(profileRepository, clothingRepository)
            )

=======
            val profileViewModel: ProfileViewModel = viewModel(factory = ProfileFactoryViewModel(profileRepository))
>>>>>>> origin/main
            val profileId = backStackEntry.arguments?.getInt("profileId") ?: 0
            AvatarConfigScreen(navController, profileViewModel, profileId)
        }

        composable(Routes.FAVORITES) { FavoritesScreen(navController) }

        composable(Routes.PROFILE_SELECTOR) {
<<<<<<< HEAD
            val profileViewModel: ProfileViewModel = viewModel(
                factory = ProfileFactoryViewModel(profileRepository, clothingRepository)
            )

=======
            val profileViewModel: ProfileViewModel = viewModel(factory = ProfileFactoryViewModel(profileRepository))
>>>>>>> origin/main
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

        // NUEVAS RUTAS PARA ADMIN USERS LIST Y FORM
        composable(Routes.ADMIN_DASHBOARD) { AdminDashboardScreen(navController) }
        composable(Routes.ADMIN_USERS_LIST) { AdminUsersListScreen(navController) }
        composable(
            route = "${Routes.ADMIN_USER_FORM}/{userId?}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            AdminUserFormScreen(navController, userId)
        }
    }
}
