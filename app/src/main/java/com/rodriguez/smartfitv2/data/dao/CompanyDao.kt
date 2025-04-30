package com.rodriguez.smartfitv2.data.dao

import androidx.room.*
import com.rodriguez.smartfitv2.data.model.CompanyEntity

@Dao
interface CompanyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Permite reemplazar un registro si ya existe
    suspend fun insert(company: CompanyEntity): Long

    @Query("SELECT * FROM company") // Obtener todos los registros de la tabla "company"
    suspend fun getAll(): List<CompanyEntity>

    @Query("SELECT * FROM company WHERE Id_Company = :id") // Obtener un registro por su ID
    suspend fun getById(id: Int): CompanyEntity?

    @Update // Actualizar un registro
    suspend fun update(company: CompanyEntity)

    @Delete // Eliminar un registro
    suspend fun delete(company: CompanyEntity)
}