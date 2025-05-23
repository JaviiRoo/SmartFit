package com.rodriguez.smartfitv2.ui.avatar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rodriguez.smartfitv2.data.database.AppDatabase
import com.rodriguez.smartfitv2.viewmodel.AvatarConfigViewModel
import com.rodriguez.smartfitv2.viewmodel.AvatarConfigViewModelFactory
import com.rodriguez.smartfitv2.viewmodel.ProfileViewModel
import com.rodriguez.smartfitv2.navigation.Routes

@Composable
fun AvatarConfigScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel,
    selectedProfileId: Int // <-- Recibe el id del perfil seleccionado
) {
    // Instancia el ViewModel usando el Factory manual (por perfil)
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val factory = remember { AvatarConfigViewModelFactory(db.avatarPartDao()) }
    val avatarConfigViewModel: AvatarConfigViewModel = viewModel(factory = factory)

    // Cada vez que cambia el perfil, actualiza el ViewModel
    LaunchedEffect(selectedProfileId) {
        avatarConfigViewModel.setActiveProfileId(selectedProfileId)
    }

    // Observa el estado persistente de Room para el perfil activo
    val avatarParts by avatarConfigViewModel.avatarParts.collectAsState()

    // Procesa los datos para pintar los cuadros
    val partes = MutableList(4) { false }
    val medidas = MutableList<String?>(4) { null }
    avatarParts.forEach { part ->
        partes[part.partIndex] = true
        medidas[part.partIndex] = part.medida
    }

    var parteSeleccionada by remember { mutableStateOf<Int?>(null) }

    // Observa el resultado del QR y actualiza la parte correspondiente
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val parteActualizada = savedStateHandle
        ?.getLiveData<Pair<Int, String>>("parte_actualizada")
        ?.observeAsState(initial = null)

    LaunchedEffect(parteActualizada?.value) {
        parteActualizada?.value?.let { (parteIndex, medida) ->
            avatarConfigViewModel.registrarMedida(parteIndex, medida)
            savedStateHandle?.remove<Pair<Int, String>>("parte_actualizada")
        }
    }

    // UI principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Fondo blanco
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
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Parte 1")
                            medidas[0]?.let { Text(it) }
                        }
                    }
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
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Parte 2")
                            medidas[1]?.let { Text(it) }
                        }
                    }
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
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Parte 3")
                            medidas[2]?.let { Text(it) }
                        }
                    }
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
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Parte 4")
                            medidas[3]?.let { Text(it) }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                if (parteSeleccionada != null) {
                    navController.currentBackStackEntry?.savedStateHandle?.set("parte_seleccionada", parteSeleccionada)
                    navController.navigate(Routes.QRSCANNER)
                }
            },
            enabled = parteSeleccionada != null,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Registrar medida por QR")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón "Listo" para volver a la Home del perfil
        Button(
            onClick = {
                navController.navigate(
                    Routes.HOME_WITH_ARG.replace("{profileId}", selectedProfileId.toString())
                ) {
                    popUpTo(Routes.AVATAR_CONFIG_WITH_ARG) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text("Listo")
        }
    }
}
