package com.rodriguez.smartfitv2.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.rodriguez.smartfitv2.data.model.Profile
import com.rodriguez.smartfitv2.data.repository.ProfileRepository
import com.rodriguez.smartfitv2.navigation.Routes
import com.rodriguez.smartfitv2.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSelectorScreen(
    navController: NavHostController,
    profileRepository: ProfileRepository,
    profileViewModel: ProfileViewModel
) {
    val scope = rememberCoroutineScope()
    val profilesState by profileViewModel.profiles.collectAsState(initial = null)
    val isLoading = profileViewModel.isLoading

    // Copia local inmutable de la propiedad delegada
    val profilesStateDelegate = profilesState

    // Recarga perfiles al entrar en la pantalla
    LaunchedEffect(Unit) {
        profileViewModel.loadProfiles()
    }

    // Solo navega a crear perfil si la carga ha terminado, la lista está cargada y vacía
    LaunchedEffect(isLoading, profilesStateDelegate) {
        if (!isLoading && profilesStateDelegate != null && profilesStateDelegate.isEmpty()) {
            navController.navigate(Routes.CREATE_PROFILE) {
                popUpTo(Routes.PROFILE_SELECTOR) { inclusive = true }
            }
        }
    }

    // Muestra un loading mientras se cargan los perfiles
    if (isLoading || profilesStateDelegate == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val profiles: List<Profile> = profilesStateDelegate

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Selecciona un Perfil") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Routes.CREATE_PROFILE)
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Nuevo Perfil")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(profiles) { profile ->
                    ProfileCard(
                        profile = profile,
                        onSelect = {
                            profileViewModel.selectProfile(profile.id)
                            navController.navigate(
                                Routes.HOME_WITH_ARG.replace("{profileId}", profile.id.toString())
                            ) {
                                popUpTo(Routes.PROFILE_SELECTOR) { inclusive = true }
                            }
                        },
                        onEdit = {
                            navController.navigate(
                                Routes.CREATE_PROFILE_WITH_ID.replace("{profileId}", profile.id.toString())
                            )
                        },
                        onDelete = {
                            scope.launch {
                                profileViewModel.deleteProfile(profile)
                                profileViewModel.loadProfiles()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileCard(
    profile: Profile,
    onSelect: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            if (!profile.image.isNullOrEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(profile.image),
                    contentDescription = "Imagen de perfil",
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(profile.name, style = MaterialTheme.typography.bodyLarge)
                Text(profile.gender.name, style = MaterialTheme.typography.bodyMedium)
            }

            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Add, contentDescription = "Editar perfil")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar perfil")
            }
        }
    }
}
