package com.rodriguez.smartfitv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rodriguez.smartfitv2.data.dao.AvatarPartDao

class AvatarConfigViewModelFactory(
    private val avatarPartDao: AvatarPartDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AvatarConfigViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AvatarConfigViewModel(avatarPartDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
