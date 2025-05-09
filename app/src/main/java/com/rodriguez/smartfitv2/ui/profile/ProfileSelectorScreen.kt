package com.rodriguez.smartfitv2.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.rodriguez.smartfitv2.data.model.Profile
import com.rodriguez.smartfitv2.data.repository.ProfileRepository
import kotlinx.coroutines.launch

@Composable
fun ProfileSelectorScreen(navController: NavHostController, profileRepository: ProfileRepository) {
    val profiles: List<Profile> by profileRepository.getAllProfilesFlow().collectAsStateWithLifecycle(initialValue = emptyList())
    var showDeleteDialog by remember { mutableStateOf(false) }
    var profileToDelete by remember { mutableStateOf<Profile?>(null) }
    val coroutineScope = rememberCoroutineScope()

    if (profiles.isEmpty()) {
        EmptyProfileView(navController)
    } else {
        ProfileGridView(
            navController = navController,
            profiles = profiles,
            onDeleteRequest = { profile ->
                profileToDelete = profile
                showDeleteDialog = true
            }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("¿Eliminar perfil?") },
            text = { Text("Esta acción no se puede deshacer") },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            profileToDelete?.let { profile ->
                                profileRepository.deleteProfile(profile)
                                showDeleteDialog = false
                            }
                        }
                    }
                ) { Text("Confirmar") }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) { Text("Cancelar") }
            }
        )
    }
}

@Composable
private fun ProfileGridView(
    navController: NavController,
    profiles: List<Profile>,
    onDeleteRequest: (Profile) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(56.dp))
        Text("Selecciona un perfil", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(32.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(profiles) { profile ->
                ProfileGridItem(
                    profile = profile,
                    onEdit = { navController.navigate("createProfile/${profile.id}") },
                    onDelete = { onDeleteRequest(profile) },
                    onClick = {
                        navController.navigate("home") {
                            popUpTo("profileSelector") { inclusive = true }
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        CreateNewProfileButton(navController)
    }
}

@Composable
private fun ProfileGridItem(
    profile: Profile,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .aspectRatio(0.9f)
            .clickable { onClick() }
    ) {
        // Imagen circular y nombre debajo
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Imagen circular
                if (!profile.image.isNullOrBlank()) {
                    Image(
                        painter = rememberAsyncImagePainter(profile.image),
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray, CircleShape)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )
                }
                // Acciones pegadas a la derecha de la imagen
                Column(
                    modifier = Modifier.padding(start = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = { onEdit() },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Editar perfil"
                        )
                    }
                    IconButton(
                        onClick = { onDelete() },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Eliminar perfil",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = profile.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun CreateNewProfileButton(navController: NavController) {
    TextButton(onClick = { navController.navigate("createProfile") }) {
        Text("Crear nuevo perfil")
    }
}

@Composable
private fun EmptyProfileView(navController: NavController) {
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
}