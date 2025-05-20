// CreateProfileScreen.kt
package com.rodriguez.smartfitv2.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.rodriguez.smartfitv2.R
import com.rodriguez.smartfitv2.data.model.Gender
import com.rodriguez.smartfitv2.data.model.Profile
import com.rodriguez.smartfitv2.data.repository.ProfileRepository
import com.rodriguez.smartfitv2.navigation.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfileScreen(
    navController: NavHostController,
    profileRepository: ProfileRepository,
    profileId: Int? = null
) {
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(Gender.HOMBRE) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isLoaded by remember { mutableStateOf(false) }
    val isEditMode = profileId != null

    val magenta = Color(0xFFD81B60)
    val white = Color.White

    val picker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    // Cargar datos si editando
    LaunchedEffect(profileId) {
        if (isEditMode && !isLoaded) {
            profileRepository.getAllProfiles().find { it.id == profileId }?.let {
                name = it.name
                gender = it.gender
                imageUri = it.image?.let(Uri::parse)
            }
            isLoaded = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(0f to white, 1f to white.copy(alpha = 0.9f)))
    ) {
        Image(
            painter = painterResource(R.drawable.login_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (isEditMode) "Editar Perfil" else "Crear Perfil",
                style = MaterialTheme.typography.headlineMedium,
                color = magenta
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = magenta, cursorColor = magenta
                )
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Gender.values().forEach { g ->
                    FilterChip(
                        selected = gender == g,
                        onClick = { gender = g },
                        label = { Text(g.name) },
                        shape = RoundedCornerShape(12.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = magenta.copy(alpha = 0.2f),
                            selectedLabelColor = magenta
                        )
                    )
                }
            }

            Button(
                onClick = { picker.launch("image/*") },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = magenta)
            ) {
                Icon(Icons.Default.PhotoCamera, contentDescription = null, tint = white)
                Spacer(Modifier.width(8.dp))
                Text("Seleccionar foto", color = white)
            }

            imageUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(white, CircleShape)
                )
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    scope.launch {
                        val profile = Profile(
                            id = profileId ?: 0,
                            name = name,
                            gender = gender,
                            image = imageUri?.toString()
                        )
                        if (isEditMode) {
                            profileRepository.updateProfile(profile)
                            withContext(Dispatchers.Main) {
                                navController.navigate(Routes.PROFILE_SELECTOR) {
                                    popUpTo(Routes.PROFILE_SELECTOR) { inclusive = true }
                                }
                            }
                        } else {
                            profileRepository.insertProfile(profile)
                            withContext(Dispatchers.Main) {
                                navController.navigate(Routes.HOME) {
                                    popUpTo(Routes.CREATE_PROFILE) { inclusive = true }
                                }
                            }
                        }
                    }
                },
                enabled = name.isNotBlank(),
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = magenta)
            ) {
                Text(if (isEditMode) "Actualizar perfil" else "Guardar perfil", color = white)
            }
        }
    }
}
