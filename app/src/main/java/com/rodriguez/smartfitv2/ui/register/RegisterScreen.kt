package com.rodriguez.smartfitv2.ui.register

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rodriguez.smartfitv2.R
import com.rodriguez.smartfitv2.ui.theme.SmartFitPink
import com.rodriguez.smartfitv2.ui.theme.montserratFontFamily
import com.rodriguez.smartfitv2.viewmodel.RegisterViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(navController: NavController, registerViewModel: RegisterViewModel = viewModel()) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf<String?>(null) }

    // Estado del selector de fecha
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        yearRange = IntRange(1900, java.time.LocalDate.now().year),
        initialDisplayMode = DisplayMode.Picker
    )
    var mostrarSelectorFecha by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = "Imagen de fondo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Crear Cuenta",
                fontFamily = montserratFontFamily,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(16.dp)
            ) {
                @Composable
                fun campoDeTexto(
                    valor: String,
                    alCambiar: (String) -> Unit,
                    etiqueta: String,
                    tipoTeclado: KeyboardType = KeyboardType.Text,
                    esCampoFecha: Boolean = false
                ) {
                    val modificador = if (esCampoFecha) {
                        Modifier
                            .fillMaxWidth()
                            .clickable { mostrarSelectorFecha = true }
                    } else {
                        Modifier.fillMaxWidth()
                    }

                    OutlinedTextField(
                        value = valor,
                        onValueChange = if (!esCampoFecha) alCambiar else { _ -> },
                        label = { Text(etiqueta, color = Color.White) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = tipoTeclado),
                        modifier = modificador,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SmartFitPink,
                            unfocusedBorderColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = SmartFitPink,
                            focusedLabelColor = SmartFitPink,
                            unfocusedLabelColor = Color.White
                        ),
                        readOnly = esCampoFecha,
                        enabled = !esCampoFecha
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                campoDeTexto(nombre, { nombre = it }, "Nombre")
                campoDeTexto(apellido, { apellido = it }, "Apellido")
                campoDeTexto(email, { email = it }, "Correo electrónico", KeyboardType.Email)
                campoDeTexto(contraseña, { contraseña = it }, "Contraseña", KeyboardType.Password)
                campoDeTexto(fechaNacimiento, { fechaNacimiento = it }, "Fecha de nacimiento", esCampoFecha = true)
                campoDeTexto(telefono, { telefono = it }, "Teléfono", KeyboardType.Phone)

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
                        containerColor = SmartFitPink,
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Registrarse", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("¿Ya tienes cuenta? Inicia sesión", color = SmartFitPink)
                }

                mensajeError?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        it,
                        color = Color(0xFFFF6B6B),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }

    if (mostrarSelectorFecha) {
        DatePickerDialog(
            onDismissRequest = { mostrarSelectorFecha = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            fechaNacimiento = formatoFecha.format(Date(it))
                        }
                        mostrarSelectorFecha = false
                    }
                ) {
                    Text("Aceptar", color = SmartFitPink)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { mostrarSelectorFecha = false }
                ) {
                    Text("Cancelar", color = SmartFitPink)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}


