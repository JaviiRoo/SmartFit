package com.rodriguez.smartfitv2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.ColumnInfo
import androidx.room.Index

@Entity(
    tableName = "Man_Measurements",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["ID_User"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["ID_User"])]  // Aquí agregamos el índice
)
data class MedidasHombre(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID_Measures") val idMeasures: Int = 0,
    @ColumnInfo(name = "ID_User") val idUser: Int,
    val height: Double?,
    val weight: Double?,
    val chestContour: Double?,
    val waistContour: Double?,
    val hipContour: Double?,
    val armLength: Double?,
    val legLength: Double?,
    val shoulderWidth: Double?
)