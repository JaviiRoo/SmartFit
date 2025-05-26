package com.rodriguez.smartfitv2.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rodriguez.smartfitv2.data.model.User
import com.rodriguez.smartfitv2.navigation.Routes
import com.rodriguez.smartfitv2.viewmodel.AdminUserViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUsersListScreen(
    navController: NavController,
    viewModel: AdminUserViewModel = viewModel()
) {
    val users = viewModel.users
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage
    val successMessage by viewModel.successMessage
    val coroutineScope = rememberCoroutineScope()

    var userToDelete by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadUsers()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Usuarios registrados") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Navegar a crear usuario sin id
                navController.navigate(Routes.ADMIN_USER_FORM)
            }) {
                Text("+")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                errorMessage != null -> {
                    Text(
                        errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    if (users.isEmpty()) {
                        Text(
                            "No hay usuarios registrados.",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(users, key = { it.id }) { user ->
                                UserListItem(
                                    user = user,
                                    onEdit = {
                                        // Navegar con el id para editar usuario
                                        navController.navigate("${Routes.ADMIN_USER_FORM}/${user.id}")
                                    },
                                    onDelete = {
                                        userToDelete = user
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Diálogo para confirmar borrado
            if (userToDelete != null) {
                AlertDialog(
                    onDismissRequest = { userToDelete = null },
                    title = { Text("Eliminar usuario") },
                    text = { Text("¿Estás seguro que quieres eliminar a ${userToDelete?.name} ${userToDelete?.surname}?") },
                    confirmButton = {
                        TextButton(onClick = {
                            userToDelete?.let {
                                coroutineScope.launch {
                                    viewModel.deleteUser(it)
                                }
                            }
                            userToDelete = null
                        }) {
                            Text("Eliminar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { userToDelete = null }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }

        // Limpiar mensajes de éxito para evitar repetirlos
        LaunchedEffect(successMessage) {
            if (successMessage != null) {
                // Aquí puedes mostrar un Snackbar o Toast si quieres
                viewModel.clearMessages()
            }
        }
    }
}

@Composable
fun UserListItem(
    user: User,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "${user.name} ${user.surname}", style = MaterialTheme.typography.titleMedium)
                Text(text = user.email, style = MaterialTheme.typography.bodyMedium)
                // Mostrar más info si quieres aquí, por ejemplo:
                Text(text = "Tel: ${user.telephone}", style = MaterialTheme.typography.bodySmall)
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Filled.Edit, contentDescription = "Editar usuario")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar usuario", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
