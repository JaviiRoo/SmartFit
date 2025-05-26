package com.rodriguez.smartfitv2.ui.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rodriguez.smartfitv2.R

data class ClothingItem(val name: String, val category: String, val image: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(navController: NavController) {
    val allItems = listOf(
        ClothingItem("Camiseta ajustada", "Camisetas", R.drawable.ic_profile_placeholder),
        ClothingItem("Pantalón vaquero", "Pantalones", R.drawable.ic_profile_placeholder),
        ClothingItem("Chaqueta técnica", "Chaquetas", R.drawable.ic_profile_placeholder),
        ClothingItem("Camiseta oversize", "Camisetas", R.drawable.ic_profile_placeholder)
    )

    var selectedCategory by remember { mutableStateOf("Todos") }

    val categories = listOf("Todos", "Camisetas", "Pantalones", "Chaquetas")
    val filteredItems = if (selectedCategory == "Todos") allItems else allItems.filter { it.category == selectedCategory }

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
            CategoryFilter(categories, selectedCategory) { selectedCategory = it }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filteredItems) { item ->
                    ClothingCard(item)
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
fun ClothingCard(item: ClothingItem) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = item.image),
                contentDescription = item.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )
            Column {
                Text(item.name, fontWeight = FontWeight.Bold)
                Text("Categoría: ${item.category}", color = Color.Gray)
            }
        }
    }
}
