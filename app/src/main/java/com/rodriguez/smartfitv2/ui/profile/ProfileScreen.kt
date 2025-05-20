// ProfileScreen.kt
package com.rodriguez.smartfitv2.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rodriguez.smartfitv2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    userName: String = "Usuario",
    email: String = "usuario@smartfit.com"
) {
    val magenta = Color(0xFFD81B60)
    val white = Color.White

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil de Usuario", color = magenta) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = magenta)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("createProfile") }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar", tint = magenta)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = white)
            )
        },
        containerColor = white
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_placeholder),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(magenta.copy(alpha = 0.1f), CircleShape)
            )

            Text(text = userName, style = MaterialTheme.typography.headlineSmall, color = magenta)
            Text(text = email, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = magenta)
            ) {
                Text("Ir al inicio", color = white)
            }
        }
    }
}
