package com.rodriguez.smartfitv2.ui.splash

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

@Composable
fun SplashScreen(navController: NavController) {
    val magenta = Color(0xFFE6007E)
    val backgroundColor = Color(0xFF111111)

    // Estado para controlar la navegación
    LaunchedEffect(key1 = true) {
        delay(4000) // Espera 4 segundos
        // Navega a la pantalla de login después del splash
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    // Animación de la barra de progreso
    val progressAnimation = remember { Animatable(0f) }
    LaunchedEffect(key1 = true) {
        delay(1700) // Espera antes de iniciar la animación
        progressAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(2000, easing = LinearEasing)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Letras SMARTFIT
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 30.dp)
            ) {
                val letters = listOf("S", "M", "A", "R", "T", "F", "I", "T")
                letters.forEachIndexed { index, letter ->
                    AnimatedLetter(letter = letter, delayMillis = index * 200, magenta = magenta)
                }
            }

            // Barra de progreso
            val barAlpha by animateFloatAsState(
                targetValue = if (progressAnimation.value > 0f) 1f else 0f,
                animationSpec = tween(500)
            )

            Box(
                modifier = Modifier
                    .width(200.dp)
                    .height(4.dp)
                    .alpha(barAlpha)
                    .background(Color(0xFF333333))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(200.dp * progressAnimation.value)
                        .background(magenta)
                )
            }
        }
    }
}

@Composable
fun AnimatedLetter(letter: String, delayMillis: Int, magenta: Color) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        delay(delayMillis.toLong())
        visible = true
    }

    val translateY by animateFloatAsState(
        targetValue = if (visible) 0f else 20f,
        animationSpec = tween(500, easing = LinearOutSlowInEasing)
    )

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(500)
    )

    Text(
        text = letter,
        color = magenta,
        fontSize = 42.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier
            .alpha(alpha)
            .offset(y = translateY.dp)
    )
}
