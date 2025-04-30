package com.rodriguez.smartfitv2.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rodriguez.smartfitv2.data.Converters
import com.rodriguez.smartfitv2.data.model.*

@TypeConverters(Converters::class)
@Database(
    entities = [
        User::class,
        MedidasHombre::class,
        MedidasMujer::class,
        CompanyEntity::class,
        APIIntegrationEntity::class,
        CompanyUserInteractionEntity::class
    ],
    version = 8
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun medidasHombreDao(): MedidasHombreDao
    abstract fun medidasMujerDao(): MedidasMujerDao
    abstract fun companyDao(): CompanyDao
    abstract fun apiIntegrationDao(): APIIntegrationDao
    abstract fun companyUserInteractionDao(): CompanyUserInteractionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smartfit_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}