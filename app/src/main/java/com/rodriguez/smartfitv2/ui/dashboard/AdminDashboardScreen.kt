package com.rodriguez.smartfitv2.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rodriguez.smartfitv2.navigation.Routes
import com.rodriguez.smartfitv2.ui.theme.SmartFitPink

@Composable
fun AdminDashboardScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Panel de administración", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate(Routes.ADMIN_USERS_LIST) },
            colors = ButtonDefaults.buttonColors(containerColor = SmartFitPink),
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Gestionar usuarios")
        }

        // Aquí podrías añadir más botones para otras acciones de admin
    }
}
