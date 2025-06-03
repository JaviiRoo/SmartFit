package com.rodriguez.smartfitv2.ui.home

<<<<<<< HEAD
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
=======
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
>>>>>>> origin/main
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
<<<<<<< HEAD
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
=======
import androidx.compose.material.icons.filled.*
>>>>>>> origin/main
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
<<<<<<< HEAD
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
=======
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
>>>>>>> origin/main
import androidx.navigation.NavController
import com.rodriguez.smartfitv2.navigation.Routes
import com.rodriguez.smartfitv2.ui.theme.SmartFitv2Theme
import com.rodriguez.smartfitv2.viewmodel.AvatarConfigViewModel
import com.rodriguez.smartfitv2.viewmodel.ProfileViewModel
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel,
    avatarConfigViewModel: AvatarConfigViewModel,
    selectedProfileId: Int
) {
    SmartFitv2Theme {
<<<<<<< HEAD
=======
        // Colores personalizados
        val primaryColor = Color(0xFFE91E63) // Magenta vibrante
        val secondaryColor = Color(0xFF9C27B0) // Púrpura
        val backgroundColor = Color(0xFF121212) // Fondo oscuro
        val surfaceColor = Color(0xFF1E1E1E) // Superficie oscura
        val textColor = Color.White
        val accentColor = Color(0xFFFF4081) // Magenta más claro

        // Estado del perfil
>>>>>>> origin/main
        val currentProfile by profileViewModel.currentProfile.collectAsState()
        var searchQuery by remember { mutableStateOf("") }
        val userName = currentProfile?.name ?: "Usuario"
<<<<<<< HEAD
        val scale = remember { Animatable(0.8f) }
        val avatarParts by avatarConfigViewModel.avatarParts.collectAsState()

        // Limpieza y extracción de medidas
        val measures: List<Int?> = (0..3).map { index ->
            avatarParts.firstOrNull { it.partIndex == index }
                ?.medida
                ?.replace("[^\\d]".toRegex(), "") // Elimina todo lo que no sea dígito
                ?.toIntOrNull()
        }.also {
            Log.d("DEBUG_HOME", "Medidas limpias: $it")
        }

        val userWaist = measures.getOrNull(0)
        val userChest = measures.getOrNull(1)
        val userHip = measures.getOrNull(2)
        val userLeg = measures.getOrNull(3)

        Log.d("DEBUG_HOME", "selectedProfileId: $selectedProfileId")
        Log.d("DEBUG_HOME", "avatarParts: $avatarParts")

        LaunchedEffect(selectedProfileId) {
            avatarConfigViewModel.setActiveProfileId(selectedProfileId)
        }
=======

        // Animaciones
        val infiniteTransition = rememberInfiniteTransition()
        val glowAnimation by infiniteTransition.animateFloat(
            initialValue = 0.8f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
>>>>>>> origin/main

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(
                    colors = listOf(backgroundColor, surfaceColor)
                ))
                .systemBarsPadding()
        ) {
            // Fondo decorativo
            FloatingBackgroundElements()

            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header
                HomeHeader(
                    userName = userName,
                    primaryColor = primaryColor,
                    onProfileClick = { navController.navigate(Routes.PROFILE) },
                    onMenuClick = { /* TODO: abrir drawer */ }
                )

                // Contenido central
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Hero Button con animación mejorada
                    AnimatedHeroButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        onClick = {
                            navController.navigate(
                                Routes.AVATAR_CONFIG_WITH_ARG.replace(
                                    "{profileId}",
                                    selectedProfileId.toString()
                                )
                            )
                        },
                        primaryColor = primaryColor,
                        secondaryColor = secondaryColor
                    )

                    // Cards de acciones rápidas
                    ActionCardsRow(navController, primaryColor, surfaceColor)
                }

                // Footer con barra de búsqueda
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
<<<<<<< HEAD
                    // Barra de búsqueda: NAVEGA PASANDO LAS MEDIDAS
                    SearchBarWithIcons(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onSearchClick = {
                            Log.d("DEBUG_HOME", "Navegando con medidas: Waist: $userWaist, Chest: $userChest, Hip: $userHip, Leg: $userLeg")
                            navController.navigate(
                                "catalog_screen/${searchQuery}/${userWaist ?: -1}/${userChest ?: -1}/${userHip ?: -1}/${userLeg ?: -1}"
                            )
                        },
                        onCameraClick = { navController.navigate(Routes.QRSCANNER) },
                        onMicClick = {
                            navController.navigate(
                                "catalog_screen/${searchQuery}/${userWaist ?: -1}/${userChest ?: -1}/${userHip ?: -1}/${userLeg ?: -1}"
                            )
                        }
=======
                    // Barra de búsqueda mejorada
                    EnhancedSearchBar(
                        onSearchClick = { navController.navigate(Routes.CATALOG) },
                        onCameraClick = { navController.navigate(Routes.QRSCANNER) },
                        onMicClick = { /* TODO: Acción micrófono */ },
                        primaryColor = primaryColor,
                        textColor = textColor
>>>>>>> origin/main
                    )

                    // Botón de cambio de perfil
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(Routes.PROFILE_SELECTOR) {
                                popUpTo(Routes.HOME_WITH_ARG) { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        containerColor = surfaceColor,
                        elevation = FloatingActionButtonDefaults.elevation(8.dp)
                    ) {
                        Text(
                            text = "Cambiar Perfil",
                            color = primaryColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

<<<<<<< HEAD
// ... (mantén aquí tus composables auxiliares como SearchBarWithIcons, TypewriterPlaceholder, HeroTypewriterButton, etc. tal como los tienes)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarWithIcons(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onCameraClick: () -> Unit,
    onMicClick: () -> Unit
) {
    val purple = Color(0xFF9C27B0)
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            TypewriterPlaceholder(
                text = "DIME LO QUE BUSCAS",
                color = purple
=======
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
>>>>>>> origin/main
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

@Composable
private fun HomeHeader(
    userName: String,
    primaryColor: Color,
    onProfileClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón de menú
        IconButton(
            onClick = onMenuClick,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(1.dp, primaryColor.copy(alpha = 0.3f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menú",
                tint = primaryColor,
                modifier = Modifier.size(24.dp)
            )
        }

        // Saludo con animación
        var showGreeting by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(300)
            showGreeting = true
        }

        AnimatedVisibility(
            visible = showGreeting,
            enter = fadeIn() + slideInVertically { -20 },
            exit = fadeOut() + slideOutVertically { -20 }
        ) {
            Text(
                text = "¡Hola, $userName!",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        // Botón de perfil
        IconButton(
            onClick = onProfileClick,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(1.dp, primaryColor.copy(alpha = 0.3f), CircleShape)
                .background(primaryColor.copy(alpha = 0.1f))
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Perfil",
                tint = primaryColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun AnimatedHeroButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    primaryColor: Color,
    secondaryColor: Color
) {
    val fullText = "CONFIGURA TU AVATAR PARA UNA EXPERIENCIA ÚNICA"
    val typingSpeed = 80L
    val blinkDuration = 2000L
    val blinkInterval = 400L

    var displayedText by remember { mutableStateOf("") }
    var blinking by remember { mutableStateOf(false) }
    var blinkVisible by remember { mutableStateOf(true) }

    // Animación de máquina de escribir
    LaunchedEffect(Unit) {
        while (true) {
            for (i in 1..fullText.length) {
                displayedText = fullText.substring(0, i)
                delay(typingSpeed)
            }

            blinking = true
            repeat((blinkDuration / blinkInterval).toInt()) {
                blinkVisible = !blinkVisible
                delay(blinkInterval)
            }
            blinking = false
            blinkVisible = true
            displayedText = ""
            delay(400)
        }
    }

    // Animación de escala y brillo
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val glow by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 16f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                shadowElevation = glow
            }
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = primaryColor.copy(alpha = 0.5f),
                spotColor = secondaryColor.copy(alpha = 0.5f)
            )
            .clickable(onClick = onClick)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(primaryColor, secondaryColor),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(1.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Avatar",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = buildAnnotatedString {
                    if (displayedText.isNotEmpty()) {
                        if (displayedText.contains("ÚNICA") && blinking) {
                            val baseText = displayedText.substringBefore("ÚNICA")
                            append(baseText)
                            withStyle(
                                style = SpanStyle(
                                    color = if (blinkVisible) Color.White else Color.Transparent,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            ) {
                                append("ÚNICA")
                            }
                        } else {
                            append(displayedText)
                        }
                    }
                },
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ActionCardsRow(
    navController: NavController,
    primaryColor: Color,
    surfaceColor: Color
) {
    val actions = listOf(
        Triple("Favoritos", Icons.Filled.Favorite, Routes.FAVORITES),
        Triple("Historial", Icons.Filled.History, Routes.MEASUREMENT_HISTORY),
        Triple("Estadísticas", Icons.Filled.Insights, Routes.CATALOG) // Asume que tienes esta ruta
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        actions.forEach { (title, icon, route) ->
            ActionCard(
                title = title,
                icon = icon,
                onClick = { navController.navigate(route) },
                primaryColor = primaryColor,
                surfaceColor = surfaceColor
            )
        }
    }
}

@Composable
private fun ActionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    primaryColor: Color,
    surfaceColor: Color
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val elevation by animateDpAsState(
        targetValue = if (isPressed) 4.dp else 8.dp,
        animationSpec = tween(100)
    )

    Card(
        modifier = Modifier
            .fillMaxHeight(1f)
            .height(120.dp)
            .graphicsLayer {
                scaleX = if (isPressed) 0.95f else 1f
                scaleY = if (isPressed) 0.95f else 1f
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = surfaceColor,
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation,
            pressedElevation = 4.dp
        ),
        border = BorderStroke(1.dp, primaryColor.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = primaryColor,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EnhancedSearchBar(
    onSearchClick: () -> Unit,
    onCameraClick: () -> Unit,
    onMicClick: () -> Unit,
    primaryColor: Color,
    textColor: Color
) {
    var query by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }

    val borderColor by animateColorAsState(
        targetValue = if (isActive) primaryColor else primaryColor.copy(alpha = 0.5f),
        animationSpec = tween(300)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(28.dp),
                spotColor = primaryColor.copy(alpha = 0.2f)
            )
            .background(Color.Transparent, RoundedCornerShape(28.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(28.dp)
            )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono de búsqueda
            IconButton(
                onClick = onSearchClick,
                modifier = Modifier
                    .size(56.dp)
                    .padding(start = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Buscar",
                    tint = primaryColor
                )
            }

            // Campo de texto
            Box(
                modifier = Modifier.weight(1f)
            ) {
                if (query.isEmpty()) {
                    TypewriterPlaceholder(
                        modifier = Modifier.align(Alignment.CenterStart),
                        text = "Buscar en SmartFit...",
                        color = primaryColor.copy(alpha = 0.7f)
                    )
                }

                BasicTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { isActive = it.isFocused },
                    textStyle = LocalTextStyle.current.copy(
                        color = textColor,
                        fontSize = 16.sp
                    ),
                    singleLine = true,
                    interactionSource = interactionSource,
                    decorationBox = { innerTextField ->
                        innerTextField()
                    }
                )
            }

            // Iconos de acciones
            Row {
                IconButton(
                    onClick = onCameraClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CameraAlt,
                        contentDescription = "Cámara",
                        tint = primaryColor.copy(alpha = 0.8f),
                        modifier = Modifier.size(24.dp)
                    )
                }

                IconButton(
                    onClick = onMicClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Mic,
                        contentDescription = "Micrófono",
                        tint = primaryColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TypewriterPlaceholder(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    speed: Long = 80L
) {
    var displayedText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            for (i in 1..text.length) {
                displayedText = text.substring(0, i)
                delay(speed)
            }
            delay(2000)
            displayedText = ""
            delay(500)
        }
    }

    Text(
        text = displayedText,
        color = color,
<<<<<<< HEAD
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontStyle = FontStyle.Italic
    )
}

@Composable
fun HeroTypewriterButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val fullText = "CONFIGURA TU AVATAR PARA UNA EXPERIENCIA UNICA"
    val typingSpeed = 100L
    val blinkDuration = 2000L // tiempo que parpadea "UNICA" (en ms)
    val blinkInterval = 400L

    val uniqueWord = "UNICA"
    val baseText = fullText.removeSuffix(uniqueWord)
    val baseLength = baseText.length
    val totalLength = fullText.length

    var displayedText by remember { mutableStateOf("") }
    var blinking by remember { mutableStateOf(false) }
    var blinkVisible by remember { mutableStateOf(true) }

    // Animación de escala (rebote)
    val scale = remember { Animatable(0.8f) }
    LaunchedEffect(Unit) {
        scale.animateTo(
            1.1f,
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
        )
        scale.animateTo(
            1f,
            animationSpec = tween(durationMillis = 300)
        )
    }

// Animación de resplandor
    val infiniteTransition = rememberInfiniteTransition()
    val glow by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAnim"  // label fuera de infiniteRepeatable
    )

    // Efecto máquina de escribir + parpadeo
    LaunchedEffect(Unit) {
        while (true) {
            // Escribir texto carácter a carácter
            for (i in 1..totalLength) {
                displayedText = fullText.substring(0, i)
                delay(typingSpeed)
            }
            // Parpadeo de "UNICA"
            blinking = true
            repeat((blinkDuration / blinkInterval).toInt()) {
                blinkVisible = true
                delay(blinkInterval / 2)
                blinkVisible = false
                delay(blinkInterval / 2)
            }
            blinking = false
            // Borrar texto y reiniciar
            displayedText = ""
            delay(400)
        }
    }

    // Construir el texto con parpadeo de "UNICA"
    val annotatedString = buildAnnotatedString {
        if (displayedText.length <= baseLength) {
            append(displayedText)
        } else if (displayedText.length < totalLength) {
            append(displayedText)
        } else {
            append(baseText)
            withStyle(
                style = SpanStyle(
                    color = Color.White.copy(alpha = if (blinking && !blinkVisible) 0f else 1f),
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(uniqueWord)
            }
        }
    }

    Box(
        modifier = modifier
            .shadow(24.dp, RoundedCornerShape(32.dp), ambientColor = Color(0xFF9C27B0).copy(alpha = 0.3f))
            .graphicsLayer {
                scaleX = scale.value * glow
                scaleY = scale.value * glow
            }
            .fillMaxWidth(0.9f)
            .height(90.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF9C27B0), Color(0xFF7C4DFF))
                ),
                shape = RoundedCornerShape(32.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = annotatedString,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { onClick() }
        )
    }
}
=======
        fontSize = 16.sp,
        modifier = modifier
    )
}
>>>>>>> origin/main
