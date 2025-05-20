package com.rodriguez.smartfitv2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rodriguez.smartfitv2.data.model.Profile
import com.rodriguez.smartfitv2.data.model.User
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rodriguez.smartfitv2.data.model.GenderConverter
import com.rodriguez.smartfitv2.data.dao.ProfileDao
import com.rodriguez.smartfitv2.data.dao.UserDao

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE profile ADD COLUMN isSelected INTEGER NOT NULL DEFAULT 0")
    }
}
@TypeConverters(GenderConverter::class)
@Database(
    entities = [
        Profile::class,
        User::class
    ],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
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
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }

        }
    }
}