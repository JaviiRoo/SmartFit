package com.rodriguez.smartfitv2.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rodriguez.smartfitv2.R
import com.rodriguez.smartfitv2.ui.theme.SmartFitv2Theme

@Composable
fun HomeScreen(navController: NavController, userName: String = "Usuario", userProfileImage: Int = R.drawable.ic_profile_placeholder) {
    SmartFitv2Theme {
        Box(modifier = Modifier.fillMaxSize()) {
            // Imagen de fondo
            Image(
                painter = painterResource(id = R.drawable.fondoprobador2),
                contentDescription = "Fondo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            IconButton(
                onClick = { navController.navigate("profile") },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Perfil",
                    tint = Color.White
                )
            }

            // Cinta métrica + texto
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)  // Alineación de la cinta métrica
                    .padding(top = 200.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cinta),
                    contentDescription = "Cinta métrica",
                    modifier = Modifier.size(64.dp)
                )
                Text(
                    text = "¿Tomamos medidas?",
                    color = Color(0xFF9C27B0),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Disposición de los botones y barra de búsqueda
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)  // Alineación de la columna
                    .padding(bottom = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Barra de búsqueda
                SearchBarWithIcons(
                    onSearchClick = {
                        navController.navigate("catalog")
                    },
                    onCameraClick = {
                        navController.navigate("qrscanner")
                    },
                    onMicClick = { Log.d("SFIT", "Micrófono pulsado") }
                )

                Spacer(modifier = Modifier.height(32.dp))  // Espacio entre los elementos

                // Botón de "Ver favoritos"
                Button(
                    onClick = { navController.navigate("favorites") },
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Ver favoritos", color = Color.White)
                }

                // Botón de "Historial de Medidas"
                Button(
                    onClick = { navController.navigate("measurement_history") },
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Historial de Medidas", color = Color.White)
                }

                // Botón de "Cerrar sesión"
                Button(
                    onClick = {
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Cerrar sesión", color = Color.White)
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchBarWithIcons(
    onSearchClick: () -> Unit,
    onCameraClick: () -> Unit,  // No necesitamos NavController aquí, solo la función
    onMicClick: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    val purple = Color(0xFF9C27B0)
    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        placeholder = {
            Text(
                text = "¿Qué buscas?",
                color = purple,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
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
                        .clickable { onCameraClick() }  // Navegar al escáner al hacer clic
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
            .fillMaxWidth(0.85f)
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
