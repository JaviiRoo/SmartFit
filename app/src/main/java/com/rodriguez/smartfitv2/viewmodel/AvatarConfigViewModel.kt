package com.rodriguez.smartfitv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodriguez.smartfitv2.data.model.AvatarPartEntity
import com.rodriguez.smartfitv2.data.dao.AvatarPartDao
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AvatarConfigViewModel(
    private val avatarPartDao: AvatarPartDao
) : ViewModel() {

    // Estado del perfil activo
    private val _activeProfileId = MutableStateFlow<Int?>(null)
    fun setActiveProfileId(profileId: Int) {
        _activeProfileId.value = profileId
    }

    // Flow que emite las partes del perfil activo
    val avatarParts: StateFlow<List<AvatarPartEntity>> = _activeProfileId
        .filterNotNull()
        .flatMapLatest { profileId ->
            avatarPartDao.getAllPartsFlow(profileId)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Registrar o actualizar medida para perfil activo
    fun registrarMedida(partIndex: Int, medida: String) {
        val profileId = _activeProfileId.value ?: return
        viewModelScope.launch {
            val part = AvatarPartEntity(
                profileId = profileId,
                partIndex = partIndex,
                medida = medida
            )
            avatarPartDao.insertOrUpdatePart(part)
        }
    }
}
