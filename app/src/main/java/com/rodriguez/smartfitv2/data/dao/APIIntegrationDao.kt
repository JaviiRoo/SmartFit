package com.rodriguez.smartfitv2.data.dao

import androidx.room.*
import com.rodriguez.smartfitv2.data.model.APIIntegrationEntity

@Dao
interface APIIntegrationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntegration(integration: APIIntegrationEntity)

    @Query("SELECT * FROM API_Integration WHERE ID_Company = :companyId")
    suspend fun getIntegrationsByCompany(companyId: Int): List<APIIntegrationEntity>

    @Update
    suspend fun updateIntegration(integration: APIIntegrationEntity)

    @Delete
    suspend fun deleteIntegration(integration: APIIntegrationEntity)
}