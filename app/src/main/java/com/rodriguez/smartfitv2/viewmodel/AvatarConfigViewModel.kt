package com.rodriguez.smartfitv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodriguez.smartfitv2.data.model.AvatarPartEntity
import com.rodriguez.smartfitv2.data.dao.AvatarPartDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AvatarConfigViewModel(
    private val avatarPartDao: AvatarPartDao
) : ViewModel() {

    // Flow que expone el estado actual de la base de datos
    val avatarParts: StateFlow<List<AvatarPartEntity>> =
        avatarPartDao.getAllPartsFlow()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Registrar o actualizar una medida
    fun registrarMedida(index: Int, medida: String) {
        viewModelScope.launch {
            avatarPartDao.insertOrUpdatePart(AvatarPartEntity(index, medida))
        }
    }
}