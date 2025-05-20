package com.rodriguez.smartfitv2.ui.measurement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MeasurementRecord(
    val date: Date,
    val chest: Float,
    val waist: Float,
    val hips: Float
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeasurementHistoryScreen(navController: NavController) {
    val measurements = listOf(
        MeasurementRecord(Date(), 98.2f, 84.5f, 96.3f),
        MeasurementRecord(Date(System.currentTimeMillis() - 86400000L * 3), 99.0f, 85.0f, 97.0f),
        MeasurementRecord(Date(System.currentTimeMillis() - 86400000L * 7), 97.5f, 83.8f, 95.6f)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial de Medidas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(measurements) { record ->
                MeasurementCard(record)
            }
        }
    }
}

@Composable
fun MeasurementCard(record: MeasurementRecord) {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Fecha: ${formatter.format(record.date)}", style = MaterialTheme.typography.bodyMedium)
            Text("Pecho: ${record.chest} cm")
            Text("Cintura: ${record.waist} cm")
            Text("Caderas: ${record.hips} cm")
        }
    }
}
