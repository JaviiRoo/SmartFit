package com.rodriguez.smartfitv2.data.repository

import com.rodriguez.smartfitv2.data.dao.CompanyUserInteractionDao
import com.rodriguez.smartfitv2.data.model.CompanyUserInteractionEntity

class CompanyUserInteractionRepository(private val dao: CompanyUserInteractionDao) {

    suspend fun insertInteraction(interaction: CompanyUserInteractionEntity) {
        dao.insertInteraction(interaction)
    }

    suspend fun updateInteraction(interaction: CompanyUserInteractionEntity) {
        dao.updateInteraction(interaction)
    }

    suspend fun deleteInteraction(interaction: CompanyUserInteractionEntity) {
        dao.deleteInteraction(interaction)
    }

    suspend fun getAllInteractions(): List<CompanyUserInteractionEntity> {
        return dao.getAllInteractions()
    }

    suspend fun getInteractionsByUser(userId: Int): List<CompanyUserInteractionEntity> {
        return dao.getInteractionsByUser(userId)
    }

    suspend fun getInteractionsByCompany(companyId: Int): List<CompanyUserInteractionEntity> {
        return dao.getInteractionsByCompany(companyId)
    }

    suspend fun getInteractionByUserAndCompany(userId: Int, companyId: Int): List<CompanyUserInteractionEntity> {
        return dao.getInteractionByUserAndCompany(userId, companyId)
    }
}