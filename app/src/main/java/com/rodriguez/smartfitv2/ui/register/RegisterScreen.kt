package com.rodriguez.smartfitv2.ui.register

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rodriguez.smartfitv2.viewmodel.RegisterViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel = viewModel()
) {
    // Colores base
    val primaryColor = Color(0xFFE91E63)
    val secondaryColor = Color(0xFF9C27B0)
    val backgroundColor = Color(0xFF121212)
    val surfaceColor = Color(0xFF1E1E1E)
    val textColor = Color.White

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf<String?>(null) }

    // Date picker state
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        yearRange = IntRange(1900, LocalDate.now().year),
        initialDisplayMode = DisplayMode.Picker
    )
    var showDatePicker by remember { mutableStateOf(false) }

    // Animación de fondo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(backgroundColor, surfaceColor)
                )
            )
    ) {
        // Elementos flotantes decorativos
        FloatingBackgroundElements(primaryColor, secondaryColor)

        // Animación de aparición
        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            delay(300)
            visible = true
        }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + slideInVertically { it / 2 },
            exit = fadeOut() + slideOutVertically { it / 2 }
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                "Crear Cuenta",
                                color = textColor,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Cerrar",
                                    tint = textColor
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            titleContentColor = textColor
                        )
                    )
                }
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        color = surfaceColor.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(16.dp),
                        tonalElevation = 8.dp,
                        shadowElevation = 8.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            InputField(
                                label = "Nombre",
                                value = nombre,
                                onValueChange = { nombre = it },
                                primaryColor = primaryColor,
                                textColor = textColor
                            )
                            InputField(
                                label = "Apellido",
                                value = apellido,
                                onValueChange = { apellido = it },
                                primaryColor = primaryColor,
                                textColor = textColor
                            )
                            InputField(
                                label = "Correo electrónico",
                                value = email,
                                onValueChange = { email = it },
                                keyboardType = KeyboardType.Email,
                                primaryColor = primaryColor,
                                textColor = textColor
                            )
                            InputField(
                                label = "Contraseña",
                                value = contraseña,
                                onValueChange = { contraseña = it },
                                keyboardType = KeyboardType.Password,
                                primaryColor = primaryColor,
                                textColor = textColor
                            )
                            DateField(
                                label = "Fecha de nacimiento",
                                value = fechaNacimiento,
                                onClick = { showDatePicker = true },
                                primaryColor = primaryColor,
                                textColor = textColor
                            )
                            InputField(
                                label = "Teléfono",
                                value = telefono,
                                onValueChange = { telefono = it },
                                keyboardType = KeyboardType.Phone,
                                primaryColor = primaryColor,
                                textColor = textColor
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    registerViewModel.registrarUsuario(
                                        nombre.trim(),
                                        apellido.trim(),
                                        email.trim(),
                                        contraseña.trim(),
                                        fechaNacimiento.trim(),
                                        telefono.trim(),
                                        onSuccess = { navController.navigate("login") },
                                        onError = { mensajeError = it }
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = primaryColor,
                                    contentColor = textColor
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Registrarse", fontWeight = FontWeight.Bold)
                            }

                            mensajeError?.let {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = it,
                                    color = Color(0xFFFF6B6B),
                                    modifier = Modifier.fillMaxWidth(),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            val fmt = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            fechaNacimiento = fmt.format(Date(it))
                        }
                        showDatePicker = false
                    }) {
                        Text("Aceptar", color = primaryColor)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancelar", color = primaryColor)
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@Composable
private fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    primaryColor: Color,
    textColor: Color
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = textColor) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primaryColor,
            unfocusedBorderColor = textColor,
            focusedLabelColor = primaryColor,
            unfocusedLabelColor = textColor,
            cursorColor = primaryColor,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}

@Composable
private fun DateField(
    label: String,
    value: String,
    onClick: () -> Unit,
    primaryColor: Color,
    textColor: Color
) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(label, color = textColor) },
        singleLine = true,
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = "Seleccionar fecha",
                tint = primaryColor
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primaryColor,
            unfocusedBorderColor = textColor,
            focusedLabelColor = primaryColor,
            unfocusedLabelColor = textColor,
            cursorColor = primaryColor,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp)
    )
}

@Composable
private fun FloatingBackgroundElements(
    primaryColor: Color,
    secondaryColor: Color
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val infiniteTransition = rememberInfiniteTransition()
        val angle1 by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(20000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
        val angle2 by infiniteTransition.animateFloat(
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
                .offset(x = (-60).dp, y = (-60).dp)
                .rotate(angle1)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(primaryColor.copy(alpha = 0.1f), Color.Transparent),
                        radius = 100f
                    ),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = 120.dp, y = 220.dp)
                .rotate(angle2)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(secondaryColor.copy(alpha = 0.1f), Color.Transparent),
                        radius = 150f
                    ),
                    shape = CircleShape
                )
        )
    }
}
