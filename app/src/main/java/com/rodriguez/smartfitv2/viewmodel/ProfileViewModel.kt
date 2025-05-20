package com.rodriguez.smartfitv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodriguez.smartfitv2.data.model.Profile
import com.rodriguez.smartfitv2.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {
    private val _profiles = MutableStateFlow<List<Profile>>(emptyList())
    val profiles: StateFlow<List<Profile>> = _profiles

    val currentProfile: StateFlow<Profile?> = repository
        .getSelectedProfileFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun loadProfiles() {
        viewModelScope.launch {
            _profiles.value = repository.getAllProfiles()
        }
    }

    fun addProfile(profile: Profile) {
        viewModelScope.launch {
            repository.insertProfile(profile)
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

    fun selectProfile(profileId: Int) {
        viewModelScope.launch {
            repository.setSelectedProfile(profileId)
        }
    }
}
