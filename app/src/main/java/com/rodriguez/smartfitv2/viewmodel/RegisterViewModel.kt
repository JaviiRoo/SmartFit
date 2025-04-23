package com.rodriguez.smartfitv2.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rodriguez.smartfitv2.data.dao.AppDatabase
import com.rodriguez.smartfitv2.data.model.User
import com.rodriguez.smartfitv2.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()
    private val repository = UserRepository(userDao)
    //Registrar usuario
    fun registerUser(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val user = User(email, password)
                repository.registerUser(user)
                Log.d("RegisterViewModel", "Usuario registrado: $email")
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Error al registrar el usuario")
            }
        }
    }
}
