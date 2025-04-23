package com.rodriguez.smartfitv2.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rodriguez.smartfitv2.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(navController: NavController, registerViewModel: RegisterViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }  // Campo para el nombre
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Crear Cuenta", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(24.dp))

            // Campo para el nombre
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre completo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón Aceptar (para registrarse)
            Button(
                onClick = {
                    // Ahora pasamos 'name', 'email' y 'password'
                    registerViewModel.registerUser(
                        name,
                        email,
                        password,
                        onSuccess = {
                            navController.navigate("login") // Navegar a login después de registrar
                        },
                        onError = { error ->
                            errorMessage = error
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Aceptar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Cancelar (para cancelar el registro y volver)
            OutlinedButton(
                onClick = {
                    navController.popBackStack() // Navegar hacia atrás (cancelar registro)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Cancelar")
            }

            // Mostrar error si existe
            errorMessage?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}