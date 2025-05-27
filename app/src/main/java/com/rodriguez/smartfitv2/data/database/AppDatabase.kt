package com.rodriguez.smartfitv2.data.database

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rodriguez.smartfitv2.data.dao.AvatarPartDao
import com.rodriguez.smartfitv2.data.dao.ProfileDao
import com.rodriguez.smartfitv2.data.dao.UserDao
import com.rodriguez.smartfitv2.data.model.AvatarPartEntity
import com.rodriguez.smartfitv2.data.model.GenderConverter
import com.rodriguez.smartfitv2.data.model.Profile
import com.rodriguez.smartfitv2.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE profile ADD COLUMN isSelected INTEGER NOT NULL DEFAULT 0")
    }
}

@TypeConverters(GenderConverter::class)
@Database(
    entities = [
        Profile::class,
        User::class,
        AvatarPartEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun userDao(): UserDao
    abstract fun avatarPartDao(): AvatarPartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // 1. Preparamos el builder
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smartfit_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d("AppDatabase", "onCreate ejecutado - insertando admin")

                            // 2. Usamos directamente la instancia capturada más abajo
                            //    (no INSTANCE?.userDao(), que es null aquí)
                            adminInserter?.invoke()
                        }
                    })

                // 3. Construimos la base de datos y la asignamos a INSTANCE
                val instance = builder.build()
                INSTANCE = instance

                // 4. Preparamos la función que inserta el admin, capturando 'instance'
                adminInserter = {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val adminUser = User(
                                name = "Admin",
                                surname = "Master",
                                email = "admin@smartfit.com",
                                password = "admin123",
                                birthday = "1970-01-01",
                                gender = "Otro",
                                registrationDate = "2024-01-01",
                                lastConnection = System.currentTimeMillis(),
                                country = "España",
                                city = "Madrid",
                                telephone = 123456789.toString(),
                                role = "admin"
                            )
                            instance.userDao().insertUser(adminUser)
                            Log.d("AppDatabase", "Usuario administrador insertado correctamente")
                        } catch (e: Exception) {
                            Log.e("AppDatabase", "Error insertando admin: ${e.message}")
                        }
                    }
                }

                instance
            }
        }

        // Variable auxiliar donde guardamos el inserter para poder llamarlo desde el callback
        private var adminInserter: (() -> Unit)? = null
    }

}
