package com.rodriguez.smartfitv2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rodriguez.smartfitv2.data.AppDatabase
import com.rodriguez.smartfitv2.data.model.User
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()

    private fun formatDate(timestamp: Long): String {
        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        return sdf.format(java.util.Date(timestamp))
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{8,}$")
        return regex.matches(password)
    }

    private fun isNumeric(input: String): Boolean {
        return input.all { it.isDigit() }
    }

    fun registerUser(
        name: String,
        surname: String,
        email: String,
        password: String,
        birthday: String,
        gender: String,
        country: String,
        city: String,
        telephone: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (name.isBlank() || surname.isBlank() || email.isBlank() || password.isBlank() || telephone.isBlank()) {
                    onError("Todos los campos obligatorios deben estar completos.")
                    return@launch
                }

                if (!isValidEmail(email)) {
                    onError("El correo electrónico no es válido.")
                    return@launch
                }

                if (!isValidPassword(password)) {
                    onError("La contraseña debe tener mínimo 8 caracteres, incluir letras, números y símbolos.")
                    return@launch
                }

                if (!isNumeric(telephone)) {
                    onError("El teléfono debe contener solo números.")
                    return@launch
                }

                val existingUser = userDao.getUserByEmail(email)
                if (existingUser != null) {
                    onError("Ya existe un usuario con este correo electrónico.")
                    return@launch
                }

                val user = User(
                    name = name,
                    surname = surname,
                    email = email,
                    password = password,
                    birthday = birthday,
                    gender = gender,
                    country = country,
                    city = city,
                    telephone = telephone.toIntOrNull() ?: 0,
                    registration_date = formatDate(System.currentTimeMillis()),
                    last_connection = System.currentTimeMillis()
                )

                userDao.insertUser(user)
                onSuccess()
            } catch (e: Exception) {
                onError("Error al registrar el usuario: ${e.message}")
            }
        }
    }
}
