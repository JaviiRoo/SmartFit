package com.rodriguez.smartfitv2.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rodriguez.smartfitv2.data.AppDatabase
import com.rodriguez.smartfitv2.data.model.User
import com.rodriguez.smartfitv2.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()
    private val repository = UserRepository(userDao)
    private fun formatDate(timestamp: Long): String {
        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        return sdf.format(java.util.Date(timestamp))
    }

    //Registrar usuario
    fun registerUser(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val currentTimestamp = System.currentTimeMillis()

                val user = User(
                    name = name,
                    email = email,
                    password = password,
                    registration_date = formatDate(currentTimestamp),
                    last_connection = currentTimestamp
                )

                repository.registerUser(user)
                Log.d("RegisterViewModel", "Usuario registrado: $email")
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Error al registrar el usuario")
            }
        }
    }
}