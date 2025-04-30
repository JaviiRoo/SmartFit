package com.rodriguez.smartfitv2.data.model

import java.util.Date
import androidx.room.*

@Entity(
    tableName = "Company_User_Interactions",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"], // id del User.kt
            childColumns = ["ID_User"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CompanyEntity::class,
            parentColumns = ["Id_Company"], // Id_Company del CompanyEntity.kt
            childColumns = ["ID_Company"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["ID_User"]),
        Index(value = ["ID_Company"])
    ]
)
data class CompanyUserInteractionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_Interactions")
    val idInteractions: Int = 0,

    @ColumnInfo(name = "ID_User")
    val idUser: Int,

    @ColumnInfo(name = "ID_Company")
    val idCompany: Int,

    @ColumnInfo(name = "Interaction_Date")
    val interactionDate: Date // Puede ser String o Date según cómo lo manejes en Converters
)