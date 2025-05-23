package com.rodriguez.smartfitv2.data.repository

import com.rodriguez.smartfitv2.data.dao.ProfileDao
import com.rodriguez.smartfitv2.data.model.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileRepository(private val profileDao: ProfileDao) {
    private val _selectedProfile = MutableStateFlow<Profile?>(null)

    // Flow de todos los perfiles
    suspend fun getAllProfiles(): List<Profile> = try {
        profileDao.getAllProfiles()
    } catch (e: Exception) {
        emptyList()
    }

    // Inserción, actualización, eliminación
    suspend fun insertProfile(profile: Profile): Long = // ← ahora devuelve el id
        profileDao.insertProfile(profile)

    suspend fun updateProfile(profile: Profile) =
        profileDao.updateProfile(profile)

    suspend fun deleteProfile(profile: Profile) =
        profileDao.deleteProfile(profile)

    // Obtener perfil por ID
    private suspend fun fetchProfileById(id: Int): Profile? = try {
        profileDao.getProfileById(id)
    } catch (e: Exception) {
        null
    }

    // Flow del perfil seleccionado
    fun getSelectedProfileFlow(): StateFlow<Profile?> = _selectedProfile

    // Establecer perfil seleccionado
    suspend fun setSelectedProfile(profileId: Int) {
        val profile = fetchProfileById(profileId)
        _selectedProfile.value = profile
    }

    // Flow de perfiles reactivo
    fun getAllProfilesFlow(): Flow<List<Profile>> = profileDao.getAllProfilesFlow()
}
