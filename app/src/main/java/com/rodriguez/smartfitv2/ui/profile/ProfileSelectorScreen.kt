package com.rodriguez.smartfitv2.ui.profile

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import coil.compose.*
import com.rodriguez.smartfitv2.data.model.Profile
import com.rodriguez.smartfitv2.data.repository.ProfileRepository
import com.rodriguez.smartfitv2.navigation.Routes
import com.rodriguez.smartfitv2.viewmodel.ProfileViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSelectorScreen(
    navController: NavHostController,
    profileRepository: ProfileRepository,
    profileViewModel: ProfileViewModel
) {
    val primaryColor = Color(0xFFE91E63) // Magenta vibrante
    val secondaryColor = Color(0xFF9C27B0) // Púrpura
    val backgroundColor = Color(0xFF121212) // Fondo oscuro
    val surfaceColor = Color(0xFF1E1E1E) // Superficie oscura
    val textColor = Color.White

    val scope = rememberCoroutineScope()
    val profilesState by profileViewModel.profiles.collectAsState(initial = null)
    val isLoading = profileViewModel.isLoading

    LaunchedEffect(Unit) {
        profileViewModel.loadProfiles()
    }

    LaunchedEffect(isLoading, profilesState) {
        if (!isLoading && profilesState != null && profilesState!!.isEmpty()) {
            navController.navigate(Routes.CREATE_PROFILE) {
                popUpTo(Routes.PROFILE_SELECTOR) { inclusive = true }
            }
        }
    }

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(
                colors = listOf(backgroundColor, surfaceColor)
            ))
    ) {
        FloatingBackgroundElements()

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + slideInVertically { -40 },
            exit = fadeOut() + slideOutVertically { -40 },
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    AnimatedVisibility(
                        visible = !isLoading && profilesState != null,
                        enter = fadeIn() + slideInVertically { -20 },
                        exit = fadeOut() + slideOutVertically { -20 }
                    ) {
                        TopAppBar(
                            title = {
                                Text(
                                    "Selecciona un Perfil",
                                    color = textColor,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent,
                                titleContentColor = textColor
                            ),
                            actions = {
                                FloatingActionButton(
                                    onClick = { navController.navigate(Routes.CREATE_PROFILE) },
                                    modifier = Modifier.size(40.dp),
                                    containerColor = primaryColor,
                                    elevation = FloatingActionButtonDefaults.elevation(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Nuevo Perfil",
                                        tint = Color.White
                                    )
                                }
                            }
                        )
                    }
                }
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    when {
                        isLoading -> {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(
                                    color = primaryColor,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }

                        profilesState == null -> {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Error al cargar perfiles", color = Color.White)
                            }
                        }

                        profilesState!!.isEmpty() -> {
                            // Se maneja automáticamente con la navegación
                        }

                        else -> {
                            ProfileList(
                                profiles = profilesState!!,
                                profileViewModel = profileViewModel,
                                navController = navController,
                                primaryColor = primaryColor,
                                surfaceColor = surfaceColor,
                                textColor = textColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileList(
    profiles: List<Profile>,
    profileViewModel: ProfileViewModel,
    navController: NavHostController,
    primaryColor: Color,
    surfaceColor: Color,
    textColor: Color
) {
    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(profiles) { profile ->
            AnimatedProfileCard(
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
                },
                primaryColor = primaryColor,
                surfaceColor = surfaceColor,
                textColor = textColor
            )
        }
    }
}

@Composable
private fun AnimatedProfileCard(
    profile: Profile,
    onSelect: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    primaryColor: Color,
    surfaceColor: Color,
    textColor: Color
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val elevation by animateDpAsState(
        targetValue = if (isPressed) 4.dp else 8.dp,
        animationSpec = tween(100)
    )

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(100)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                shadowElevation = elevation.toPx()
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onSelect
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = surfaceColor,
            contentColor = textColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation,
            pressedElevation = 4.dp
        ),
        border = BorderStroke(1.dp, primaryColor.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(primaryColor.copy(alpha = 0.2f))
            ) {
                if (!profile.image.isNullOrEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(profile.image),
                        contentDescription = "Imagen de perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Avatar predeterminado",
                        tint = primaryColor,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = profile.name,
                    color = textColor,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = profile.gender.name,
                    color = textColor.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row {
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar perfil",
                        tint = primaryColor
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar perfil",
                        tint = Color(0xFFF44336) // Rojo para delete
                    )
                }
            }
        }
    }
}

@Composable
private fun FloatingBackgroundElements() {
    Box(modifier = Modifier.fillMaxSize()) {
        val infiniteTransition = rememberInfiniteTransition()
        val floatAnim1 by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(20000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )

        val floatAnim2 by infiniteTransition.animateFloat(
            initialValue = 360f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(25000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-50).dp, y = (-50).dp)
                .rotate(floatAnim1)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFE91E63).copy(alpha = 0.1f),
                            Color.Transparent
                        ),
                        radius = 100f
                    ),
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = 100.dp, y = 200.dp)
                .rotate(floatAnim2)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF9C27B0).copy(alpha = 0.1f),
                            Color.Transparent
                        ),
                        radius = 150f
                    ),
                    shape = CircleShape
                )
        )
    }
}
