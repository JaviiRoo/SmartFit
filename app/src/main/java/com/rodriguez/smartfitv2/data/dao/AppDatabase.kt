package com.rodriguez.smartfitv2.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rodriguez.smartfitv2.data.model.User

@Database(entities = [User::class], version = 5)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

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
                    .fallbackToDestructiveMigration() // ⚠️ Esto borra la base si hay cambios en el esquema
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
