package com.rodriguez.smartfitv2.ui.catalog

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rodriguez.smartfitv2.data.model.ClothingItem
import com.rodriguez.smartfitv2.data.repository.ClothingRepository
import java.text.Normalizer
import kotlin.math.abs

// --- FUNCIÓN PARA NORMALIZAR TEXTO (quita tildes y pasa a minúsculas)
fun normalizeText(text: String): String {
    val temp = Normalizer.normalize(text, Normalizer.Form.NFD)
    return temp.replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "").lowercase()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController: NavController,
    searchQuery: String,
    userWaist: Int?,
    userChest: Int?,
    userHip: Int?,
    userLeg: Int?,
    clothingRepository: ClothingRepository
) {
    Log.d(
        "CATALOG",
        "searchQuery: $searchQuery, userWaist: $userWaist, userChest: $userChest, userHip: $userHip, userLeg: $userLeg"
    )

    val allItems = clothingRepository.getAllClothes()
    var selectedCategory by remember { mutableStateOf("Todos") }
    val categories = listOf("Todos", "Camisetas", "Pantalones", "Chaquetas")

    val margen = 1 // margen de cm aceptable

    // Verifica si hay al menos una medida configurada
    val algunaMedidaConfigurada = listOf(userWaist, userChest, userHip, userLeg).any { it != null }

    // --- Normaliza el texto de búsqueda para que funcione sin tildes ni mayúsculas
    val normalizedSearch = normalizeText(searchQuery)

    // Filtrado: solo si hay alguna medida configurada y hay palabra de búsqueda
    val filteredItems =
        if (!algunaMedidaConfigurada || searchQuery.isBlank()) {
            emptyList()
        } else {
            allItems.filter { item ->
                val nameMatch = normalizeText(item.name).contains(normalizedSearch)
                val waistMatch = userWaist != null && abs(item.waist - userWaist) <= margen
                val chestMatch = userChest != null && abs(item.chest - userChest) <= margen
                val hipMatch = userHip != null && abs(item.hip - userHip) <= margen
                val legMatch = userLeg != null && abs(item.leg - userLeg) <= margen

                // --- LOG para cada item
                Log.d(
                    "CATALOG",
                    "Item: ${item.name}, nameMatch: $nameMatch, waistMatch: $waistMatch, chestMatch: $chestMatch, hipMatch: $hipMatch, legMatch: $legMatch"
                )

                (selectedCategory == "Todos" || item.type == selectedCategory) &&
                        nameMatch &&
                        (waistMatch || chestMatch || hipMatch || legMatch)
            }
        }

    // --- LOG después de calcular filteredItems
    Log.d("CATALOG", "filteredItems: $filteredItems")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explorar Ropa") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = if (searchQuery.isNotBlank())
                    "Resultados para \"$searchQuery\""
                else
                    "Todos los artículos",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            CategoryFilter(categories, selectedCategory) { selectedCategory = it }

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredItems.isEmpty()) {
                Text("No se encontraron prendas para \"$searchQuery\"")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(filteredItems) { item ->
                        ClothingCard(
                            item = item,
                            userWaist = userWaist,
                            userChest = userChest,
                            userHip = userHip,
                            userLeg = userLeg,
                            margen = margen
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFilter(categories: List<String>, selected: String, onCategorySelected: (String) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        categories.forEach { category ->
            FilterChip(
                selected = selected == category,
                onClick = { onCategorySelected(category) },
                label = { Text(category) }
            )
        }
    }
}

@Composable
fun ClothingCard(
    item: ClothingItem,
    userWaist: Int?,
    userChest: Int?,
    userHip: Int?,
    userLeg: Int?,
    margen: Int
) {
    val cinturaOk = userWaist != null && abs(item.waist - userWaist) <= margen
    val pechoOk = userChest != null && abs(item.chest - userChest) <= margen
    val caderaOk = userHip != null && abs(item.hip - userHip) <= margen
    val piernaOk = userLeg != null && abs(item.leg - userLeg) <= margen

    val rojo = Color.Red
    val normal = Color.Unspecified
    val negrita = FontWeight.Bold

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(item.name, fontWeight = FontWeight.Bold)
            Text("Tipo: ${item.type}", color = Color.Gray)
            Text(
                "Cintura: ${item.waist} cm",
                color = if (userWaist != null && !cinturaOk) rojo else normal,
                fontWeight = if (cinturaOk) negrita else null
            )
            Text(
                "Pecho: ${item.chest} cm",
                color = if (userChest != null && !pechoOk) rojo else normal,
                fontWeight = if (pechoOk) negrita else null
            )
            Text(
                "Cadera: ${item.hip} cm",
                color = if (userHip != null && !caderaOk) rojo else normal,
                fontWeight = if (caderaOk) negrita else null
            )
            Text(
                "Pierna: ${item.leg} cm",
                color = if (userLeg != null && !piernaOk) rojo else normal,
                fontWeight = if (piernaOk) negrita else null
            )
            Text("Stock: ${item.stock}")

            // Mostrar la nota si alguna medida está en negrita o rojo
            if (listOf(userWaist, userChest, userHip, userLeg).any { it != null }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Información",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "Las medidas resaltadas en negrita coinciden con esta prenda",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
