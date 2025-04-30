package com.rodriguez.smartfitv2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "company")
data class CompanyEntity(
    @PrimaryKey(autoGenerate = true)
    val Id_Company: Int = 0,
    val Company_Name: String,
    val API_Key: String,
    val Registration_day: Date,
    val Account_Status: String
)
