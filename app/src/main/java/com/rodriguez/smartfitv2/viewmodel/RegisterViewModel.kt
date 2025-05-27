package com.rodriguez.smartfitv2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rodriguez.smartfitv2.data.database.AppDatabase
import com.rodriguez.smartfitv2.data.model.User
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()

    private fun formatearFecha(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    private fun esEmailValido(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun esContraseñaValida(contraseña: String): Boolean {
        val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{8,}$")
        return regex.matches(contraseña)
    }

    private fun esNumerico(input: String): Boolean {
        return input.matches(Regex("\\d+"))
    }

    private fun esFechaValida(fecha: String): Boolean {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(fecha)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun registrarUsuario(
        nombre: String,
        apellido: String,
        email: String,
        contraseña: String,
        fechaNacimiento: String,
        telefono: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (nombre.isBlank() || apellido.isBlank() || email.isBlank() ||
                    contraseña.isBlank() || fechaNacimiento.isBlank() || telefono.isBlank()) {
                    onError("Todos los campos son obligatorios")
                    return@launch
                }

                if (nombre.length < 2 || apellido.length < 2) {
                    onError("Nombre y apellido deben tener al menos 2 caracteres")
                    return@launch
                }

                if (!esEmailValido(email)) {
                    onError("Por favor ingresa un correo electrónico válido")
                    return@launch
                }

                if (!esContraseñaValida(contraseña)) {
                    onError("La contraseña debe tener mínimo 8 caracteres, incluyendo letras, números y al menos un símbolo (@\$!%*#?&)")
                    return@launch
                }

                if (!esFechaValida(fechaNacimiento)) {
                    onError("Fecha de nacimiento no válida. Usa el formato dd/mm/aaaa")
                    return@launch
                }

                if (!esNumerico(telefono)) {
                    onError("El teléfono debe contener solo números")
                    return@launch
                }

                if (telefono.length < 9) {
                    onError("El teléfono debe tener al menos 9 dígitos")
                    return@launch
                }

                val usuarioExistente = userDao.getUserByEmail(email)
                if (usuarioExistente != null) {
                    onError("Ya existe una cuenta con este correo electrónico")
                    return@launch
                }

                val usuario = User(
                    name = nombre,
                    surname = apellido,
                    email = email,
                    password = contraseña,
                    birthday = fechaNacimiento,
                    gender = "",
                    country = "",
                    city = "",
                    telephone = (telefono.toIntOrNull() ?: 0).toString(),
                    registrationDate = formatearFecha(System.currentTimeMillis()),
                    lastConnection = System.currentTimeMillis()
                )

                userDao.insertUser(usuario)
                onSuccess()
            } catch (e: Exception) {
                onError("Error al registrar: ${e.localizedMessage ?: "Inténtalo de nuevo más tarde"}")
            }
        }
    }
}