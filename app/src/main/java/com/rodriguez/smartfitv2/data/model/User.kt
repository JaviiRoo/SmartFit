package com.rodriguez.smartfitv2.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val birthday: String = "",
    val gender: String = "",
    @ColumnInfo(name = "registration_date")
    val registrationDate: String = "",
    @ColumnInfo(name = "last_connection")
    val lastConnection: Long = 0L,
    val country: String = "",
    val city: String = "",
    val telephone: String = "",
    val role: String = "user"
)
