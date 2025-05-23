package com.rodriguez.smartfitv2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rodriguez.smartfitv2.data.model.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile")
    suspend fun getAllProfiles(): List<Profile>

    @Query("SELECT * FROM profile")
    fun getAllProfilesFlow(): Flow<List<Profile>>

    @Insert
    suspend fun insertProfile(profile: Profile): Long

    @Update
    suspend fun updateProfile(profile: Profile)

    @Delete
    suspend fun deleteProfile(profile: Profile)

    @Query("SELECT * FROM profile WHERE id = :id")
    suspend fun getProfileById(id: Int): Profile?
}
