package com.rodriguez.smartfitv2.ui.login

// Importaciones necesarias para el diseño y funcionalidad de la UI
import androidx.compose.animation.AnimatedVisibility // Para animar la aparición del mensaje de error
import androidx.compose.foundation.layout.* // Layouts básicos como Column, Spacer, etc.
import androidx.compose.foundation.shape.RoundedCornerShape // Para bordes redondeados en el botón
import androidx.compose.foundation.text.KeyboardOptions // Opciones del teclado (tipo email, password, etc.)
import androidx.compose.material.icons.Icons // Acceso al grupo de íconos
import androidx.compose.material3.* // Componentes de Material 3 como Text, Button, etc.
import androidx.compose.runtime.* // Estados como remember y mutableStateOf
import androidx.compose.ui.Alignment // Alineación dentro de layouts
import androidx.compose.ui.Modifier // Modificadores para tamaño, padding, etc.
import androidx.compose.material.icons.filled.Visibility // Ícono para mostrar contraseña
import androidx.compose.material.icons.filled.VisibilityOff // Ícono para ocultar contraseña
import androidx.compose.ui.text.input.ImeAction // Acción del teclado (Next, Done, etc.)
import androidx.compose.ui.text.input.KeyboardType // Tipo de entrada del teclado (email, password...)
import androidx.compose.ui.text.input.PasswordVisualTransformation // Oculta el texto de la contraseña
import androidx.compose.ui.text.input.VisualTransformation // Permite mostrar u ocultar texto
import androidx.compose.ui.unit.dp // Para definir medidas como padding y altura
import androidx.lifecycle.viewmodel.compose.viewModel // Para obtener el ViewModel desde Compose
import androidx.navigation.NavController // Para navegar entre pantallas
import com.rodriguez.smartfitv2.viewmodel.LoginViewModel // ViewModel específico del Login

// Función principal de la pantalla de Login
@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {

    // Estado del campo de correo electrónico
    var email by remember { mutableStateOf("") }

    // Estado del campo de contraseña
    var password by remember { mutableStateOf("") }

    // Estado para mostrar u ocultar la contraseña
    var passwordVisible by remember { mutableStateOf(false) }

    // Estado para manejar errores de login
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Validación: solo permite iniciar sesión si los campos están completos
    val isFormValid = email.isNotBlank() && password.isNotBlank()

    // Contenedor principal de la pantalla
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Layout en columna centrado vertical y horizontalmente
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp) // Espaciado lateral
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center, // Centra los elementos verticalmente
            horizontalAlignment = Alignment.CenterHorizontally // Centra horizontalmente
        ) {

            // Título de bienvenida
            Text("¡Bienvenido a SmartFit!", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(24.dp)) // Espacio debajo del título

            // Campo de texto para ingresar el email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it }, // Actualiza el estado cuando se escribe
                label = { Text("Correo electrónico") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email, // Activa teclado tipo email
                    imeAction = ImeAction.Next // "Next" en el teclado
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre campos

            // Campo de texto para ingresar la contraseña con opción para mostrar/ocultar
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                // Muestra los caracteres o los oculta dependiendo del estado
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = "Toggle password visibility")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de inicio de sesión
            Button(
                onClick = {
                    // Llama al método loginUser del ViewModel
                    loginViewModel.loginUser(
                        email,
                        password,
                        onSuccess = {
                            errorMessage = null
                            navController.navigate("profileSelector") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onError = { error ->
                            errorMessage = error // Muestra el mensaje de error
                        }
                    )
                },
                enabled = isFormValid, // Solo se habilita si los campos están completos
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp), // Tamaño moderno del botón
                shape = RoundedCornerShape(12.dp) // Bordes redondeados
            ) {
                Text("Iniciar sesión", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón de texto para ir a la pantalla de registro
            TextButton(
                onClick = { navController.navigate("register") }
            ) {
                Text("¿No tienes cuenta? Regístrate")
            }

            // Si hay un mensaje de error, lo mostramos con una animación
            AnimatedVisibility(visible = errorMessage != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = errorMessage ?: "", // Mostramos el error si existe
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
