package com.rodriguez.smartfitv2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "profile")
@TypeConverters(GenderConverter::class) // Usamos el convertidor para Gender
data class Profile(
    @PrimaryKey(autoGenerate = true) // Marcamos id como clave primaria
    val id: Long = 0L,  // Se genera automáticamente si no se proporciona
    val name: String,
    val gender: Gender,
    val image: String? = null, // Imagen opcional
    val isSelected: Boolean = false //Para identificar perfil seleccionado
)