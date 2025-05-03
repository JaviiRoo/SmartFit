package com.rodriguez.smartfitv2.data

import androidx.room.TypeConverter
import com.rodriguez.smartfitv2.data.model.Gender

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