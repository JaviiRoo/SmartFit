package com.rodriguez.smartfitv2.data.dao

import androidx.room.*
import com.rodriguez.smartfitv2.data.model.CompanyUserInteractionEntity // Cambiado el nombre de la clase aquí

@Dao
interface CompanyUserInteractionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInteraction(interaction: CompanyUserInteractionEntity) // Aquí también

    @Update
    suspend fun updateInteraction(interaction: CompanyUserInteractionEntity) // Aquí también

    @Delete
    suspend fun deleteInteraction(interaction: CompanyUserInteractionEntity) // Aquí también

    @Query("SELECT * FROM Company_User_Interactions")
    suspend fun getAllInteractions(): List<CompanyUserInteractionEntity> // Aquí también

    @Query("SELECT * FROM Company_User_Interactions WHERE ID_User = :userId")
    suspend fun getInteractionsByUser(userId: Int): List<CompanyUserInteractionEntity> // Aquí también

    @Query("SELECT * FROM Company_User_Interactions WHERE ID_Company = :companyId")
    suspend fun getInteractionsByCompany(companyId: Int): List<CompanyUserInteractionEntity> // Aquí también

    @Query("SELECT * FROM Company_User_Interactions WHERE ID_User = :userId AND ID_Company = :companyId")
    suspend fun getInteractionByUserAndCompany(userId: Int, companyId: Int): List<CompanyUserInteractionEntity> // Aquí también
}