package com.rodriguez.smartfitv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodriguez.smartfitv2.data.repository.ProfileRepository
import com.rodriguez.smartfitv2.data.model.Profile
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {

    private val _profiles = MutableStateFlow<List<Profile>>(emptyList())
    val profiles: StateFlow<List<Profile>> = _profiles

    // Cargar todos los perfiles
    fun loadProfiles() {
        viewModelScope.launch {
            _profiles.value = repository.getAllProfiles()
        }
    }

    // Crear un nuevo perfil
    fun addProfile(profile: Profile) {
        viewModelScope.launch {
            repository.insertProfile(profile)
            loadProfiles() // Recargar la lista después de insertar
        }
    }

    // Actualizar un perfil existente
    fun updateProfile(profile: Profile) {
        viewModelScope.launch {
            repository.updateProfile(profile)
            loadProfiles() // Recargar la lista después de actualizar
        }
    }

    // Eliminar un perfil
    fun deleteProfile(profile: Profile) {
        viewModelScope.launch {
            repository.deleteProfile(profile)
            loadProfiles() // Recargar la lista después de eliminar
        }
    }
}