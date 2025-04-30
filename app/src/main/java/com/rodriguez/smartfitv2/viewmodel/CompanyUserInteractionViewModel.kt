package com.rodriguez.smartfitv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodriguez.smartfitv2.data.model.CompanyUserInteractionEntity
import com.rodriguez.smartfitv2.data.repository.CompanyUserInteractionRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CompanyUserInteractionViewModel(
    private val repository: CompanyUserInteractionRepository
) : ViewModel() {

    private val _interactions = MutableStateFlow<List<CompanyUserInteractionEntity>>(emptyList())
    val interactions: StateFlow<List<CompanyUserInteractionEntity>> = _interactions

    fun loadInteractions() {
        viewModelScope.launch {
            _interactions.value = repository.getAllInteractions()
        }
    }

    fun loadInteractionsByUser(userId: Int) {
        viewModelScope.launch {
            _interactions.value = repository.getInteractionsByUser(userId)
        }
    }

    fun loadInteractionsByCompany(companyId: Int) {
        viewModelScope.launch {
            _interactions.value = repository.getInteractionsByCompany(companyId)
        }
    }

    fun addInteraction(interaction: CompanyUserInteractionEntity) {
        viewModelScope.launch {
            repository.insertInteraction(interaction)
            loadInteractions() // Recargar después de insertar
        }
    }

    fun deleteInteraction(interaction: CompanyUserInteractionEntity) {
        viewModelScope.launch {
            repository.deleteInteraction(interaction)
            loadInteractions() // Recargar después de eliminar
        }
    }

    fun updateInteraction(interaction: CompanyUserInteractionEntity) {
        viewModelScope.launch {
            repository.updateInteraction(interaction)
            loadInteractions() // Recargar después de actualizar
        }
    }
}