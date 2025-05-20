package com.rodriguez.smartfitv2.ui.home


import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rodriguez.smartfitv2.navigation.Routes
import com.rodriguez.smartfitv2.ui.theme.SmartFitv2Theme
import com.rodriguez.smartfitv2.viewmodel.ProfileViewModel
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    SmartFitv2Theme {
        val currentProfile by profileViewModel.currentProfile.collectAsState()
        val userName = currentProfile?.name ?: "Usuario"
        val scope = rememberCoroutineScope()
        val scale = remember { Animatable(0.8f) }

        LaunchedEffect(Unit) {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring()
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Si quieres un fondo blanco puro, comenta el siguiente bloque
            /*
            Image(
                painter = painterResource(id = R.drawable.fondoprobador2),
                contentDescription = "Fondo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            */

            // Fila superior: Menú hamburguesa a la izquierda y perfil a la derecha
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp, start = 16.dp, end = 16.dp), // BAJA ambos iconos para evitar la cámara
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Menú hamburguesa
                IconButton(
                    onClick = { /* TODO: abrir drawer o menú lateral */ },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.25f))
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menú",
                        tint = Color(0xFF9C27B0),
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Botón de perfil
                IconButton(
                    onClick = { navController.navigate(Routes.PROFILE) },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.25f))
                        .graphicsLayer(
                            scaleX = scale.value,
                            scaleY = scale.value
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Perfil",
                        tint = Color(0xFF9C27B0),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            // Saludo con el nombre del usuario
            Text(
                text = "¡Hola, $userName!",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 80.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )


            // Hero Button animado y llamativo (reemplaza la cinta métrica)
            HeroTypewriterButton(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 120.dp),
                onClick = {
                    navController.navigate(Routes.AVATAR_CONFIG)
                }
            )

            // Botones inferiores
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 48.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Barra de búsqueda con placeholder animado
                    SearchBarWithIcons(
                        onSearchClick = { navController.navigate(Routes.CATALOG) },
                        onCameraClick = { navController.navigate(Routes.QRSCANNER) },
                        onMicClick = { Log.d("SFIT", "Micrófono pulsado") }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { navController.navigate(Routes.FAVORITES) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Ver favoritos", color = Color.White)
                    }

                    Button(
                        onClick = { navController.navigate(Routes.MEASUREMENT_HISTORY) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("Historial de Medidas", color = Color.White)
                    }

                    Button(
                        onClick = {
                            navController.navigate(Routes.PROFILE_SELECTOR) {
                                popUpTo(Routes.HOME) { inclusive = true }
                            }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Cambiar de Perfil", color = Color.White)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarWithIcons(
    onSearchClick: () -> Unit,
    onCameraClick: () -> Unit,
    onMicClick: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    val purple = Color(0xFF9C27B0)
    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        placeholder = {
            TypewriterPlaceholder(
                text = "DIME LO QUE BUSCAS",
                color = purple
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Buscar",
                tint = purple,
                modifier = Modifier.clickable { onSearchClick() }
            )
        },
        trailingIcon = {
            Row {
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = "Cámara",
                    tint = purple,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable { onCameraClick() }
                )
                Icon(
                    imageVector = Icons.Filled.Mic,
                    contentDescription = "Micrófono",
                    tint = purple,
                    modifier = Modifier.clickable { onMicClick() }
                )
            }
        },
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = purple,
            unfocusedIndicatorColor = purple,
            cursorColor = purple,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        singleLine = true
    )
}

@Composable
fun TypewriterPlaceholder(
    text: String,
    color: Color,
    speed: Long = 60L
) {
    var displayedText by remember { mutableStateOf("") }

    // Animación infinita
    LaunchedEffect(Unit) {
        while (true) {
            for (i in 1..text.length) {
                displayedText = text.substring(0, i)
                delay(speed)
            }
            delay(1000)
            displayedText = ""
            delay(400)
        }
    }

    Text(
        text = displayedText,
        color = color,
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
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glow by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "glowAnim"
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
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Avatar",
                    tint = Color.White,
                    modifier = Modifier
                        .size(38.dp)
                        .graphicsLayer {
                            shadowElevation = 12f
                        }
                )
                Spacer(modifier = Modifier.width(18.dp))
                Text(
                    text = annotatedString,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}