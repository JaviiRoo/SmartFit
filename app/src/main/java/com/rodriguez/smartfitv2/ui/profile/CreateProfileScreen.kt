package com.rodriguez.smartfitv2.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.rodriguez.smartfitv2.data.model.Gender
import com.rodriguez.smartfitv2.data.model.Profile
import com.rodriguez.smartfitv2.data.repository.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CreateProfileScreen(
    navController: NavController,
    profileRepository: ProfileRepository
) {
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(Gender.HOMBRE) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear Perfil", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Gender.values().forEach { g ->
                FilterChip(
                    selected = gender == g,
                    onClick = { gender = g },
                    label = { Text(g.name) },
                    shape = RoundedCornerShape(8.dp)
                )
            }
        }

        Button(
            onClick = { imagePickerLauncher.launch("image/*") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
        ) {
            Text("Seleccionar imagen")
        }

        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }

        Button(
            onClick = {
                val newProfile = Profile(
                    name = name,
                    gender = gender,
                    image = imageUri?.toString()
                )

                // Guardar en la base de datos usando corrutina
                CoroutineScope(Dispatchers.IO).launch {
                    profileRepository.insertProfile(newProfile)

                    // Cambio al hilo principal para navegar sin error
                    kotlinx.coroutines.withContext(Dispatchers.Main) {
                        navController.navigate("profileSelector")
                    }
                }
            },
            enabled = name.isNotBlank()
        ) {
            Text("Guardar perfil")
        }
    }
}