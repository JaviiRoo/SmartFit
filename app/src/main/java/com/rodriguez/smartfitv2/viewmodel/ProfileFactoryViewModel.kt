package com.rodriguez.smartfitv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rodriguez.smartfitv2.data.repository.ProfileRepository
import com.rodriguez.smartfitv2.data.repository.ClothingRepository

class ProfileFactoryViewModel(
    private val profileRepository: ProfileRepository,
    private val clothingRepository: ClothingRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(profileRepository, clothingRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
