package com.rodriguez.smartfitv2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rodriguez.smartfitv2.data.database.AppDatabase
import com.rodriguez.smartfitv2.data.model.User
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class AdminUserViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()

    // Lista observable de usuarios
    val users = mutableStateListOf<User>()

    // Estado UI
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)
    val successMessage = mutableStateOf<String?>(null)

    fun loadUsers() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                // Trae todos los usuarios (sin admins, si quieres)
                val allUsers = userDao.getUsersByRole("user")
                users.clear()
                users.addAll(allUsers)
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = "Error cargando usuarios: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                userDao.deleteUser(user)
                successMessage.value = "Usuario eliminado correctamente"
                loadUsers()
            } catch (e: Exception) {
                errorMessage.value = "Error eliminando usuario: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun addUser(user: User, onComplete: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                userDao.insertUser(user)
                successMessage.value = "Usuario creado correctamente"
                loadUsers()
                onComplete()
            } catch (e: Exception) {
                errorMessage.value = "Error creando usuario: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun updateUser(user: User, onComplete: () -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                userDao.updateUser(user)
                successMessage.value = "Usuario actualizado correctamente"
                loadUsers()
                onComplete()
            } catch (e: Exception) {
                errorMessage.value = "Error actualizando usuario: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    // Limpia mensajes
    fun clearMessages() {
        errorMessage.value = null
        successMessage.value = null
    }
}
