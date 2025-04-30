package com.rodriguez.smartfitv2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.ColumnInfo

@Entity(
    tableName = "Woman_Measurements",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["ID_User"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MedidasMujer(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID_Measures") val idMeasures: Int = 0,
    @ColumnInfo(name = "ID_User") val idUser: Int,
    val height: Double?,
    val weight: Double?,
    val chestContour: Double?,
    val waistContour: Double?,
    val hipContour: Double?,
    val armLength: Double?,
    val legLength: Double?,
    val BustOutline: Double?,
    val BustSeparation: Double?,
    val BraSize: Double?,
    val FootSize: Double?,
    val UpdateDate: Double?
)
