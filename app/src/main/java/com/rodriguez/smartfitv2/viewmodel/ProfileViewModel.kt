package com.rodriguez.smartfitv2.viewmodel

import com.rodriguez.smartfitv2.data.repository.ClothingRepository
import com.rodriguez.smartfitv2.data.model.ClothingItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodriguez.smartfitv2.data.model.Profile
import com.rodriguez.smartfitv2.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository,
    private val clothingRepository: ClothingRepository
) : ViewModel() {
    private val _profiles = MutableStateFlow<List<Profile>>(emptyList())
    val profiles: StateFlow<List<Profile>> = _profiles

    val currentProfile: StateFlow<Profile?> = repository
        .getSelectedProfileFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // --- NUEVO: Estado de carga ---
    var isLoading by mutableStateOf(true)
        private set

    fun loadProfiles() {
        viewModelScope.launch {
            isLoading = true
            _profiles.value = repository.getAllProfiles()
            isLoading = false
        }
    }

    fun addProfile(profile: Profile) {
        viewModelScope.launch {
            val newId = repository.insertProfile(profile) // obteniene el ID del nuevo perfil
            repository.setSelectedProfile(newId)          // Lo selecciona como activo
            loadProfiles()
        }
    }


    fun updateProfile(profile: Profile) {
        viewModelScope.launch {
            repository.updateProfile(profile)
            loadProfiles()
        }
    }

    fun deleteProfile(profile: Profile) {
        viewModelScope.launch {
            repository.deleteProfile(profile)
            loadProfiles()
        }
    }

    fun selectProfile(profileId: Long) {
        viewModelScope.launch {
            repository.setSelectedProfile(profileId)
        }
    }
//FUNCION PARA LA CONSULTA DE BUSQUEDA QUE TENGA LA MEDIDA IGUAL AL PERFIL EN FUNCION DEL STOCK
fun searchClothes(query: String, measures: List<Int?>): List<ClothingItem> {
    return clothingRepository.getAllClothes().filter { item ->
        item.name.contains(query, ignoreCase = true) &&

                (measures[0]?.let { Math.abs(item.waist - it) <= 1 } ?: true) &&
                (measures[1]?.let { Math.abs(item.chest - it) <= 1 } ?: true) &&
                (measures[2]?.let { Math.abs(item.hip - it) <= 1 } ?: true) &&
                (measures[3]?.let { Math.abs(item.leg - it) <= 1 } ?: true) &&
                //en un futuro añadiriamos las otras partes del cuerpo a partir de aqui
                // y de la misma forma que tenemos en las lineas de arriba para adaptarlo
                //a uno de los 4 avatares
                item.stock > 0
    }
}
}