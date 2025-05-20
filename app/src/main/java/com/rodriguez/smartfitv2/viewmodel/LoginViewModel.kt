// Paquete del ViewModel de Login
package com.rodriguez.smartfitv2.viewmodel

// Importaciones necesarias para ViewModel y corrutinas
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rodriguez.smartfitv2.data.database.AppDatabase
import com.rodriguez.smartfitv2.data.repository.UserRepository
import kotlinx.coroutines.launch

// ViewModel para manejar la lógica de autenticación
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    // Obtiene el DAO del usuario desde la base de datos
    private val userDao = AppDatabase.getDatabase(application).userDao()

    // Crea una instancia del repositorio del usuario
    private val repository = UserRepository(userDao)

    // Función pública para intentar iniciar sesión
    fun loginUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        // Lanza una corrutina en el scope del ViewModel
        viewModelScope.launch {
            // Intenta autenticar al usuario desde el repositorio
            val user = repository.loginUser(email, password)
            // Si se encuentra un usuario válido
            if (user != null) {
                onSuccess() // Llama al callback de éxito
            } else {
                onError("Credenciales incorrectas") // Muestra error
            }
        }
    }
}
