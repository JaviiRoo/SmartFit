package com.rodriguez.smartfitv2.data.repository

import com.rodriguez.smartfitv2.data.dao.ProfileDao
import com.rodriguez.smartfitv2.data.model.Profile
import com.rodriguez.smartfitv2.data.model.Gender

class ProfileRepository(private val profileDao: ProfileDao) {

    // Obtener todos los perfiles
    suspend fun getAllProfiles(): List<Profile> {
        return try {
            profileDao.getAllProfiles()
        } catch (e: Exception) {
            // Manejo de error (podrías registrar el error o manejarlo de alguna forma)
            emptyList() // Devolver una lista vacía en caso de error
        }
    }

    // Insertar un nuevo perfil
    suspend fun insertProfile(profile: Profile) {
        try {
            profileDao.insertProfile(profile)
        } catch (e: Exception) {
            // Manejo de error si deseas registrar el error o informarlo
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
            profileDao.getProfileByType(profileType.name) // Utilizamos .name para el tipo de género
        } catch (e: Exception) {
            // Manejo de error
            emptyList() // Devolver una lista vacía en caso de error
        }
    }
}