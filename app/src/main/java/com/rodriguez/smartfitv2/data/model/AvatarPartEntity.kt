package com.rodriguez.smartfitv2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "avatar_parts")
data class AvatarPartEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val profileId: Int, //Identifica y asocia el perfil
    val partIndex: Int, // 0 a 3 para las 4 partes
    val medida: String // medida en cm, por ejemplo "32.5 cm"
)
