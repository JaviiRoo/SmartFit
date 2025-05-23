package com.rodriguez.smartfitv2.data.dao

import androidx.room.*
import com.rodriguez.smartfitv2.data.model.AvatarPartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AvatarPartDao {
    @Query("SELECT * FROM avatar_parts WHERE profileId = :profileId")
    fun getAllPartsFlow(profileId: Int): Flow<List<AvatarPartEntity>>

    @Query("SELECT * FROM avatar_parts WHERE profileId = :profileId")
    suspend fun getAllParts(profileId: Int): List<AvatarPartEntity>

    @Query("SELECT * FROM avatar_parts WHERE profileId = :profileId AND partIndex = :index LIMIT 1")
    suspend fun getPartByIndex(profileId: Int, index: Int): AvatarPartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePart(part: AvatarPartEntity)

    @Delete
    suspend fun deletePart(part: AvatarPartEntity)
}
