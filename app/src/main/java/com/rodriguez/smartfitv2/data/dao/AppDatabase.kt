package com.rodriguez.smartfitv2.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
<<<<<<< HEAD
import androidx.room.TypeConverters
import com.rodriguez.smartfitv2.data.model.User
import com.rodriguez.smartfitv2.data.model.MedidasHombre
import com.rodriguez.smartfitv2.data.model.MedidasMujer
import com.rodriguez.smartfitv2.data.model.CompanyEntity
import com.rodriguez.smartfitv2.data.model.APIIntegrationEntity
import com.rodriguez.smartfitv2.data.model.CompanyUserInteractionEntity
import com.rodriguez.smartfitv2.data.Converters

@TypeConverters(Converters::class)
@Database(entities = [User::class, MedidasHombre::class, MedidasMujer::class,
        CompanyEntity::class, APIIntegrationEntity::class, CompanyUserInteractionEntity::class], version = 8)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao // Métodos DAO para User
    abstract fun medidasHombreDao(): MedidasHombreDao // Métodos DAO para MedidasHombre
    abstract fun medidasMujerDao(): MedidasMujerDao // Métodos DAO para MedidasMujer
    abstract fun companyDao(): CompanyDao // Métodos DAO para Company
    abstract fun apiIntegrationDao(): APIIntegrationDao // Métodos DAO para apiIntegration
    abstract fun companyUserInteractionDao(): CompanyUserInteractionDao // Métodos DAO para CompanyUserInteraction
    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

=======
import com.rodriguez.smartfitv2.data.model.User

@Database(entities = [User::class], version = 5)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

>>>>>>> b2770a7067bb9e9421ba38d97edf24a1d44beb14
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smartfit_database"
                )
<<<<<<< HEAD
                    .fallbackToDestructiveMigration()
=======
                    .fallbackToDestructiveMigration() // ⚠️ Esto borra la base si hay cambios en el esquema
>>>>>>> b2770a7067bb9e9421ba38d97edf24a1d44beb14
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
