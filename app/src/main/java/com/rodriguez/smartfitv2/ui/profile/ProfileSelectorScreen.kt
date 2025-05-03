package com.rodriguez.smartfitv2.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rodriguez.smartfitv2.data.model.Profile
import com.rodriguez.smartfitv2.data.repository.ProfileRepository

@Composable
fun ProfileSelectorScreen(navController: NavController, profileRepository: ProfileRepository) {
    val profiles = remember { mutableStateListOf<Profile>() }
    var selectedProfile by remember { mutableStateOf<Profile?>(null) }

    // Cargar perfiles desde Room
    LaunchedEffect(Unit) {
        val fetchedProfiles = profileRepository.getAllProfiles()
        profiles.clear()
        profiles.addAll(fetchedProfiles)
    }

    if (profiles.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("No tienes perfiles creados. Crea uno nuevo.")

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.navigate("createProfile") }) {
                Text("Crear perfil")
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Selecciona un perfil", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(16.dp))

            profiles.forEach { profile ->
                Button(
                    onClick = {
                        selectedProfile = profile
                        navController.navigate("home") {
                            popUpTo("profileSelector") { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Perfil: ${profile.name}")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = {
                navController.navigate("createProfile")
            }) {
                Text("Crear nuevo perfil")
            }
        }
    }
}