package com.rodriguez.smartfitv2.data.repository

import kotlinx.coroutines.flow.Flow
import com.rodriguez.smartfitv2.data.dao.ProfileDao
import com.rodriguez.smartfitv2.data.model.Profile
import com.rodriguez.smartfitv2.data.model.Gender

class ProfileRepository(private val profileDao: ProfileDao) {

    // Obtener todos los perfiles
    suspend fun getAllProfiles(): List<Profile> {
        return try {
            profileDao.getAllProfiles()
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Insertar un nuevo perfil
    suspend fun insertProfile(profile: Profile) {
        try {
            profileDao.insertProfile(profile)
        } catch (e: Exception) {
            // Manejo de error
        }
    }

    // Actualizar un perfil existente
    suspend fun updateProfile(profile: Profile) {
        try {
            profileDao.updateProfile(profile)
        } catch (e: Exception) {
            // Manejo de error
        }
    }

    // Eliminar un perfil
    suspend fun deleteProfile(profile: Profile) {
        try {
            profileDao.deleteProfile(profile)
        } catch (e: Exception) {
            // Manejo de error
        }
    }

    // Buscar un perfil por tipo (HOMBRE, MUJER, NIÑO, NIÑA)
    suspend fun getProfileByType(profileType: Gender): List<Profile> {
        return try {
            profileDao.getProfileByType(profileType.name)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Observar todos los perfiles en tiempo real (Flow)
    fun getAllProfilesFlow(): Flow<List<Profile>> {
        return profileDao.getAllProfilesFlow()
    }
}