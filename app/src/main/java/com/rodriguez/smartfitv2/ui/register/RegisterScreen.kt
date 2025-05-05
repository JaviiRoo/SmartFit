package com.rodriguez.smartfitv2.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rodriguez.smartfitv2.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(navController: NavController, registerViewModel: RegisterViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Crear Cuenta", style = MaterialTheme.typography.headlineLarge, color = Color.Magenta)
            Spacer(modifier = Modifier.height(16.dp))

            @Composable
            fun outlinedField(value: String, onChange: (String) -> Unit, label: String, keyboardType: KeyboardType = KeyboardType.Text) {
                OutlinedTextField(
                    value = value,
                    onValueChange = onChange,
                    label = { Text(label) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            outlinedField(name, { name = it }, "Nombre")
            outlinedField(surname, { surname = it }, "Apellido")
            outlinedField(email, { email = it }, "Correo electrónico", KeyboardType.Email)
            outlinedField(password, { password = it }, "Contraseña", KeyboardType.Password)
            outlinedField(birthday, { birthday = it }, "Fecha de nacimiento (dd/mm/yyyy)")
            outlinedField(gender, { gender = it }, "Género")
            outlinedField(country, { country = it }, "País")
            outlinedField(city, { city = it }, "Ciudad")
            outlinedField(telephone, { telephone = it }, "Teléfono", KeyboardType.Number)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    registerViewModel.registerUser(
                        name.trim(),
                        surname.trim(),
                        email.trim(),
                        password.trim(),
                        birthday.trim(),
                        gender.trim(),
                        country.trim(),
                        city.trim(),
                        telephone.trim(),
                        onSuccess = { navController.navigate("login") },
                        onError = { errorMessage = it }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)
            ) {
                Text("Aceptar")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Magenta)
            ) {
                Text("Cancelar")
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

