// MainActivity.kt (sin cambios en la lógica de login)
package com.rodriguez.smartfitv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import com.rodriguez.smartfitv2.data.dao.AvatarPartDao
import com.rodriguez.smartfitv2.data.database.AppDatabase
import com.rodriguez.smartfitv2.data.repository.ClothingRepository
import com.rodriguez.smartfitv2.data.repository.ProfileRepository
import com.rodriguez.smartfitv2.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val profileDao = database.profileDao()
        val avatarPartDao = database.avatarPartDao()
        val profileRepository = ProfileRepository(profileDao)
        val clothingRepository = ClothingRepository()


        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                AppNavigation(
                    navController = navController,
                    profileRepository = profileRepository,
                    clothingRepository = clothingRepository,
                    avatarPartDao = avatarPartDao
                )
            }
        }

    }
}
