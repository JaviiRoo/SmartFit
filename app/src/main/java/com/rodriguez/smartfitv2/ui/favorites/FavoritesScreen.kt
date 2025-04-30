package com.rodriguez.smartfitv2.ui.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rodriguez.smartfitv2.R

data class FavoriteItem(val id: Int, val name: String, val shop: String, val imageRes: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController) {
    var favorites by remember {
        mutableStateOf(
            listOf(
                FavoriteItem(1, "Camiseta Oversize", "Zara", R.drawable.ic_profile_placeholder),
                FavoriteItem(2, "Pantalón Chino", "H&M", R.drawable.ic_profile_placeholder),
                FavoriteItem(3, "Chaqueta Técnica", "Decathlon", R.drawable.ic_profile_placeholder)
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Prendas Favoritas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(favorites, key = { it.id }) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = item.imageRes),
                            contentDescription = item.name,
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color.LightGray)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.name, style = MaterialTheme.typography.titleMedium)
                            Text("Tienda: ${item.shop}", style = MaterialTheme.typography.bodySmall)
                        }
                        IconButton(onClick = {
                            favorites = favorites.filterNot { it.id == item.id }
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }
    }
}
