package com.rodriguez.smartfitv2.viewmodel;

import android.app.Application //Accede al contexto desde el ViewModel.
import androidx.lifecycle.AndroidViewModel //Variante de ViewModel que permite acceder al contexto (ideal para bases de datos locales).
import androidx.lifecycle.viewModelScope //un CoroutineScope ligado al ciclo de vida del ViewModel.
import com.rodriguez.smartfitv2.data.dao.AppDatabase //Accede a la base de datos y a la lógica de usuario.
import com.rodriguez.smartfitv2.data.repository.UserRepository
import kotlinx.coroutines.launch //Permite correr código asincrónico de forma segura en el viewModelScope.

class LoginViewModel(application: Application) : AndroidViewModel(application) { //Define el ViewModel para la pantalla de login. Extiende de AndroidViewModel en vez de ViewModel, para poder acceder al Application (y por ende al contexto necesario para Room).

    private val userDao = AppDatabase.getDatabase(application).userDao() //Inicializa el DAO de usuario, obtiene la instancia Singleton de la base de datos y llama a userDao().
    private val repository = UserRepository(userDao) //Crea una instancia del repositorio de usuarios, que encapsula el acceso a datos y la lógica de autenticación.

    fun loginUser( // Método público que la UI llama cuando se quiere hacer login.
        email: String, //Recibe email
        password: String, //Recibe password
        onSuccess: () -> Unit, //Callback en caso de éxito donde onSuccess manda lo que se debe hacer a continuación (dirigirse a Home).
        onError: (String) -> Unit //Callback en caso de error y qué es lo que debe hacer en este caso (colocar mensaje de error).
    ) {
        viewModelScope.launch { //Ejecuta el código asincrónicamente dentro del viewModelScope y evita bloquear el hilo principal para acceder a la base de datos.
            val user = repository.loginUser(email, password) //Llama al método del repositorio, que consulta si existe un usuario con esas credenciales.
            if (user != null) { //Si encuentra el usuario, llama al callback de éxito y si no, llama al de error con un mensaje personalizado.
                onSuccess()
            } else {
                onError("Credenciales incorrectas")
            }
        }
    }
}
