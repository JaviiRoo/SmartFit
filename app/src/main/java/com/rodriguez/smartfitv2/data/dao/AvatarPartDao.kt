package com.rodriguez.smartfitv2.data.dao

import androidx.room.*
import com.rodriguez.smartfitv2.data.model.AvatarPartEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AvatarPartDao {
    @Query("SELECT * FROM avatar_parts")
    fun getAllPartsFlow(): Flow<List<AvatarPartEntity>>

    @Query("SELECT * FROM avatar_parts")
    suspend fun getAllParts(): List<AvatarPartEntity>

    @Query("SELECT * FROM avatar_parts WHERE partIndex = :index LIMIT 1")
    suspend fun getPartByIndex(index: Int): AvatarPartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdatePart(part: AvatarPartEntity)

    @Delete
    suspend fun deletePart(part: AvatarPartEntity)
}
