package com.rodriguez.smartfitv2.ui.avatar

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rodriguez.smartfitv2.navigation.Routes
import com.rodriguez.smartfitv2.viewmodel.ProfileViewModel

@Composable
fun AvatarConfigScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    var partes by remember { mutableStateOf(listOf(false, false, false, false)) }
    var parteSeleccionada by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.LightGray)
        ) {
            Column(Modifier.fillMaxSize()) {
                Row(Modifier.weight(1f)) {
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .border(
                                BorderStroke(
                                    width = 3.dp,
                                    color = if (parteSeleccionada == 0) Color.Blue else Color.Black
                                )
                            )

                            .background(if (partes[0]) Color.Green else Color.White)
                            .clickable { parteSeleccionada = 0 },
                        contentAlignment = Alignment.Center
                    ) { Text("Parte 1") }
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .border(
                                BorderStroke(
                                    width = 3.dp,
                                    color = if (parteSeleccionada == 1) Color.Blue else Color.Black
                                )
                            )

                            .background(if (partes[1]) Color.Green else Color.White)
                            .clickable { parteSeleccionada = 1 },
                        contentAlignment = Alignment.Center
                    ) { Text("Parte 2") }
                }
                Row(Modifier.weight(1f)) {
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .border(
                                BorderStroke(
                                    width = 3.dp,
                                    color = if (parteSeleccionada == 2) Color.Blue else Color.Black
                                )
                            )

                            .background(if (partes[2]) Color.Green else Color.White)
                            .clickable { parteSeleccionada = 2 },
                        contentAlignment = Alignment.Center
                    ) { Text("Parte 3") }
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .border(
                                BorderStroke(
                                    width = 3.dp,
                                    color = if (parteSeleccionada == 3) Color.Blue else Color.Black
                                )
                            )

                            .background(if (partes[3]) Color.Green else Color.White)
                            .clickable { parteSeleccionada = 3 },
                        contentAlignment = Alignment.Center
                    ) { Text("Parte 4") }
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                if (parteSeleccionada != null) {
                    navController.navigate(Routes.QRSCANNER)
                }
            },
            enabled = parteSeleccionada != null,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Registrar medida por QR")
        }
    }

    // Observa el resultado del QR y actualiza la parte correspondiente (igual que antes)
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val parteActualizada = savedStateHandle
        ?.getLiveData<String>("parte_actualizada")
        ?.observeAsState(initial = null)

    LaunchedEffect(parteActualizada?.value) {
        parteActualizada?.value?.let { medida ->
            // Marca la parte seleccionada como "con medida"
            parteSeleccionada?.let { index ->
                partes = partes.toMutableList().also { it[index] = true }
            }
            savedStateHandle?.remove<String>("parte_actualizada")
        }
    }
}
