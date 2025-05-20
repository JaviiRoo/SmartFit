package com.rodriguez.smartfitv2.data.model

import androidx.room.TypeConverter

class GenderConverter {

    @TypeConverter
    fun fromGender(gender: Gender): String {
        return gender.name // Convierte el enum a String
    }

    @TypeConverter
    fun toGender(gender: String): Gender {
        return Gender.valueOf(gender) // Convierte el String a enum
    }
}