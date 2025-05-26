package com.rodriguez.smartfitv2.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import coil.compose.*
import com.rodriguez.smartfitv2.R
import com.rodriguez.smartfitv2.data.model.Gender
import com.rodriguez.smartfitv2.data.model.Profile
import com.rodriguez.smartfitv2.data.repository.ProfileRepository
import com.rodriguez.smartfitv2.navigation.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun CreateProfileScreen(
    navController: NavHostController,
    profileRepository: ProfileRepository,
    profileId: Int? = null
) {
    // Colores personalizados
    val primaryColor = Color(0xFFE91E63) // Magenta vibrante
    val secondaryColor = Color(0xFF9C27B0) // Púrpura
    val backgroundColor = Color(0xFF121212) // Fondo oscuro
    val surfaceColor = Color(0xFF1E1E1E) // Superficie oscura
    val textColor = Color.White
    val accentColor = Color(0xFFFF4081) // Magenta más claro

    // Estado del formulario
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(Gender.HOMBRE) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isLoaded by remember { mutableStateOf(false) }
    val isEditMode = profileId != null

    val scope = rememberCoroutineScope()
    val picker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    // Animación de entrada
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    // Cargar datos si estamos en modo edición
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
            .background(brush = Brush.verticalGradient(
                colors = listOf(backgroundColor, surfaceColor)
            ))
    ) {
        // Fondo decorativo
        FloatingBackgroundElements()

        // Contenido principal con animación
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + slideInVertically { -40 },
            exit = fadeOut() + slideOutVertically { -40 },
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Título animado
                AnimatedContent(
                    targetState = isEditMode,
                    transitionSpec = {
                        fadeIn() with fadeOut()
                    }
                ) { editMode ->
                    Text(
                        text = if (editMode) "Editar Perfil" else "Crear Perfil",
                        style = MaterialTheme.typography.headlineMedium,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Imagen de perfil con animación
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(surfaceColor.copy(alpha = 0.7f))
                        .border(2.dp, primaryColor.copy(alpha = 0.5f), CircleShape)
                        .clickable { picker.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUri),
                            contentDescription = "Foto de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar predeterminado",
                            tint = primaryColor.copy(alpha = 0.5f),
                            modifier = Modifier.size(64.dp)
                        )
                    }

                    // Badge de cámara
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(primaryColor)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = "Cambiar foto",
                            tint = Color.White,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                // Campo de nombre
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = {
                        Text(
                            "Nombre",
                            color = textColor.copy(alpha = 0.8f)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = surfaceColor.copy(alpha = 0.5f),
                        unfocusedContainerColor = surfaceColor.copy(alpha = 0.3f),
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor,
                        focusedLabelColor = primaryColor,
                        unfocusedLabelColor = textColor.copy(alpha = 0.6f),
                        focusedIndicatorColor = primaryColor,
                        unfocusedIndicatorColor = primaryColor.copy(alpha = 0.5f),
                        cursorColor = primaryColor
                    ),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

                // Selector de género
                Text(
                    text = "Género",
                    color = textColor.copy(alpha = 0.8f),
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 16.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Gender.values().forEach { g ->
                        val isSelected = gender == g
                        var isPressed by remember { mutableStateOf(false) }

                        val elevation by animateDpAsState(
                            targetValue = if (isPressed) 4.dp else if (isSelected) 8.dp else 4.dp,
                            animationSpec = tween(100)
                        )

                        Card(
                            onClick = { gender = g },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) {
                                    primaryColor.copy(alpha = 0.2f)
                                } else {
                                    surfaceColor.copy(alpha = 0.5f)
                                }
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = elevation,
                                pressedElevation = 4.dp
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (isSelected) primaryColor else Color.Transparent
                            )
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = g.name,
                                    color = if (isSelected) primaryColor else textColor.copy(alpha = 0.8f),
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Botón de guardar
                var isButtonPressed by remember { mutableStateOf(false) }
                val buttonScale by animateFloatAsState(
                    targetValue = if (isButtonPressed) 0.95f else 1f,
                    animationSpec = tween(100)
                )

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
                                val newId = profileRepository.insertProfile(profile)
                                withContext(Dispatchers.Main) {
                                    navController.navigate(
                                        Routes.HOME_WITH_ARG.replace(
                                            "{profileId}",
                                            newId.toInt().toString()
                                        )
                                    ) {
                                        popUpTo(Routes.PROFILE_SELECTOR) { inclusive = true }
                                    }
                                }
                            }
                        }
                    },
                    enabled = name.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .graphicsLayer {
                            scaleX = buttonScale
                            scaleY = buttonScale
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
                        disabledContainerColor = primaryColor.copy(alpha = 0.3f)
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 4.dp
                    ),
                    interactionSource = remember { MutableInteractionSource() }.also { source ->
                        LaunchedEffect(source) {
                            source.interactions.collect {
                                when (it) {
                                    is PressInteraction.Press -> isButtonPressed = true
                                    is PressInteraction.Release -> isButtonPressed = false
                                    is PressInteraction.Cancel -> isButtonPressed = false
                                }
                            }
                        }
                    }
                ) {
                    Text(
                        text = if (isEditMode) "ACTUALIZAR PERFIL" else "CREAR PERFIL",
                        color = if (name.isNotBlank()) Color.White else textColor.copy(alpha = 0.5f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun FloatingBackgroundElements() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Elementos decorativos flotantes
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