package com.rodriguez.smartfitv2.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rodriguez.smartfitv2.ui.theme.SmartFitv2Theme

class HomeScreen {
    setContent {
        SmartFitv2Theme {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // Imagen de fondo
                Image(
                    painter = painterResource(id = R.drawable.imagen_general),
                    contentDescription = "Fondo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // TopBar (menú y perfil)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(WindowInsets.statusBars.asPaddingValues())
                        .align(Alignment.TopCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopBar(
                        onMenuClick = { Log.d("SFIT", "Menú pulsado") },
                        onProfileClick = { Log.d("SFIT", "Perfil pulsado") }
                    )
                }

                // Bloque superior: cinta métrica + texto, desplazado un poco hacia abajo
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 200.dp), // Ajusta este valor para subir o bajar el bloque
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icono_cinta),
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

                // Bloque central: SFIT centrado verticalmente
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = "SFIT",
                        style = TextStyle(
                            fontSize = 126.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD550),
                            shadow = Shadow(
                                color = Color(0xFFBCAAA4),
                                offset = Offset(4f, 4f),
                                blurRadius = 8f
                            )
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // Bloque inferior: barra de búsqueda, desplazada hacia abajo
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(top = 700.dp), // Ajusta este valor para bajar la barra
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SearchBarWithIcons(
                        onSearchClick = { Log.d("SFIT", "Lupa pulsada") },
                        onCameraClick = { Log.d("SFIT", "Cámara pulsada") },
                        onMicClick = { Log.d("SFIT", "Micrófono pulsado") }
                    )
                }
            }
        }
    }
}
}

@Composable
fun TopBar(
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val purple = Color(0xFF9C27B0)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Menú",
            tint = purple,
            modifier = Modifier
                .size(32.dp)
                .clickable { onMenuClick() }
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { onProfileClick() }
        ) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Perfil",
                tint = purple,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = "Mi perfil",
                color = purple,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarWithIcons(
    onSearchClick: () -> Unit,
    onCameraClick: () -> Unit,
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
                        .clickable { onCameraClick() }
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
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.Transparent,
            unfocusedBorderColor = purple,
            focusedBorderColor = purple,
            cursorColor = purple,
            // Si tu versión lo soporta, puedes añadir:
            // focusedTextColor = purple,
            // unfocusedTextColor = purple
        ),
        singleLine = true
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Hello $name!",
            color = Color.Yellow,
            fontSize = 96.sp,
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Prueba2_sfitTheme {
        Greeting("Android")
    }
}
}