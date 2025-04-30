package com.rodriguez.smartfitv2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.rodriguez.smartfitv2.data.model.MedidasHombre

@Dao
interface MedidasHombreDao {

    @Insert
    suspend fun insertMedida(medida: MedidasHombre)

    @Update
    suspend fun updateMedida(medida: MedidasHombre)

    @Delete
    suspend fun deleteMedida(medida: MedidasHombre)

    @Query("SELECT * FROM Man_Measurements WHERE ID_User = :userId")
    suspend fun getMedidasByUserId(userId: Int): List<MedidasHombre>
}