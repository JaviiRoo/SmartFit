// Paquete donde se encuentra esta pantalla splash
package com.rodriguez.smartfitv2.ui.splash

// Importaciones necesarias para animaciones, diseño visual y navegación
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

// Función composable que representa la pantalla de presentación (Splash)
@Composable
fun SplashScreen(navController: NavController) {
    // Definición de colores personalizados
    val magenta = Color(0xFFE6007E)
    val backgroundColor = Color(0xFF111111)

    // Efecto que se lanza una sola vez al entrar en composición
    LaunchedEffect(key1 = true) {
        // Espera 4 segundos antes de navegar a la pantalla siguiente
        delay(4000)
        // Navega a la pantalla "login" y elimina "splash" del backstack
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    // Animación que simula una barra de progreso desde 0f a 1f
    val progressAnimation = remember { Animatable(0f) }

    // Efecto lanzado para iniciar la animación de la barra después de cierto retraso
    LaunchedEffect(key1 = true) {
        // Espera 1.7 segundos antes de iniciar la animación de carga
        delay(1700)
        // Anima el progreso de 0 a 1 en 2 segundos con una interpolación lineal
        progressAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(2000, easing = LinearEasing)
        )
    }

    // Contenedor principal que ocupa toda la pantalla con fondo oscuro
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center // Centra el contenido en ambos ejes
    ) {
        // Columna que organiza los elementos verticalmente en el centro
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Fila que contiene las letras del logo "SMARTFIT"
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 30.dp) // Espacio debajo de las letras
            ) {
                // Lista de letras a mostrar
                val letters = listOf("S", "M", "A", "R", "T", "F", "I", "T")
                // Se recorre cada letra con su índice para animarlas una por una
                letters.forEachIndexed { index, letter ->
                    // Llama a la función que anima individualmente cada letra
                    AnimatedLetter(letter = letter, delayMillis = index * 200, magenta = magenta)
                }
            }

            // Estado animado que controla la opacidad de la barra de progreso
            val barAlpha by animateFloatAsState(
                targetValue = if (progressAnimation.value > 0f) 1f else 0f,
                animationSpec = tween(500) // Duración de la animación de aparición
            )

            // Contenedor de la barra de fondo gris oscuro
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(4.dp)
                    .alpha(barAlpha) // Aparece gradualmente
                    .background(Color(0xFF333333))
            ) {
                // Barra animada que crece horizontalmente según el progreso
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(200.dp * progressAnimation.value) // Ancho animado
                        .background(magenta) // Color de la barra
                )
            }
        }
    }
}

// Función que anima una letra individual con desvanecimiento y movimiento vertical
@Composable
fun AnimatedLetter(letter: String, delayMillis: Int, magenta: Color) {
    // Estado que controla la visibilidad (para iniciar la animación)
    var visible by remember { mutableStateOf(false) }

    // Lanza un efecto una sola vez para activar la animación con un retraso
    LaunchedEffect(key1 = true) {
        delay(delayMillis.toLong()) // Retraso individual por letra
        visible = true // Activa la animación
    }

    // Animación de posición vertical: empieza más abajo y sube
    val translateY by animateFloatAsState(
        targetValue = if (visible) 0f else 20f, // Si es visible, sube a 0
        animationSpec = tween(500, easing = LinearOutSlowInEasing) // Suaviza la entrada
    )

    // Animación de opacidad: de transparente a visible
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(500) // Duración de aparición
    )

    // Componente de texto con animaciones aplicadas
    Text(
        text = letter,
        color = magenta, // Color personalizado
        fontSize = 42.sp, // Tamaño grande de letra
        fontWeight = FontWeight.ExtraBold, // Peso grueso
        modifier = Modifier
            .alpha(alpha) // Opacidad animada
            .offset(y = translateY.dp) // Movimiento vertical animado
    )
}
