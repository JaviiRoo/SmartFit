package com.rodriguez.smartfitv2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rodriguez.smartfitv2.data.model.Profile

@Dao
interface ProfileDao {

    // Obtener todos los perfiles
    @Query("SELECT * FROM profile")
    suspend fun getAllProfiles(): List<Profile>

    // Insertar un nuevo perfil
    @Insert
    suspend fun insertProfile(profile: Profile)

    // Actualizar un perfil existente
    @Update
    suspend fun updateProfile(profile: Profile)

    // Eliminar un perfil
    @Delete
    suspend fun deleteProfile(profile: Profile)

    // Buscar un perfil por tipo (HOMBRE, MUJER, NIÑO, NIÑA)
    @Query("SELECT * FROM profile WHERE gender = :profileType")
    suspend fun getProfileByType(profileType: String): List<Profile>
}