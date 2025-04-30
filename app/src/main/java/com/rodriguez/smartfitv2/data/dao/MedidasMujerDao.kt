package com.rodriguez.smartfitv2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.rodriguez.smartfitv2.data.model.MedidasMujer

@Dao
interface MedidasMujerDao {

    @Insert
    suspend fun insertMedida(medida: MedidasMujer)

    @Update
    suspend fun updateMedida(medida: MedidasMujer)

    @Delete
    suspend fun deleteMedida(medida: MedidasMujer)

    @Query("SELECT * FROM Woman_Measurements WHERE ID_User = :userId")
    suspend fun getMedidasByUserId(userId: Int): List<MedidasMujer>
}