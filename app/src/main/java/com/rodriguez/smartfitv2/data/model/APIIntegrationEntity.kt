package com.rodriguez.smartfitv2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.ColumnInfo

@Entity(
    tableName = "API_Integration",
    foreignKeys = [ForeignKey(
        entity = CompanyEntity::class,
        parentColumns = ["ID_Company"],
        childColumns = ["ID_Company"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class APIIntegrationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_Integration")
    val idIntegration: Int = 0,

    @ColumnInfo(name = "ID_Company", index = true)
    val idCompany: Int,

    @ColumnInfo(name = "API_Version")
    val apiVersion: String,

    @ColumnInfo(name = "Active_EndPoints")
    val activeEndpoints: String,

    @ColumnInfo(name = "Last_Update")
    val lastUpdate: String
)