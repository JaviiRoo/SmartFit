package com.rodriguez.smartfitv2.data.dao

import android.content.Context //Import de Room necesario para crear la base de datos.
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rodriguez.smartfitv2.data.model.User //Entidad que se guarda en la base de datos.

@Database(entities = [User::class], version = 3) // Anotación DataBase de Room donde entities muestra las clases que son tablas y versión de la base de datos que va aumentando conforme haces cambios a la base de datos.
abstract class AppDatabase : RoomDatabase() { //Clase abstracta que hereda de RoomDataBase, que es la clase base y principal.

    abstract fun userDao(): UserDao //Método abstracto del DAO

    companion object { //Bloque estático que permite tener una única instancia compartida de la base de datos (patrón Singleton).
        @Volatile private var INSTANCE: AppDatabase? = null //Variable que guarda la instancia de la base de datos. //@Volatile: garantiza que los cambios a esta variable se reflejen correctamente en todos los hilos.
        fun getDatabase(context: Context): AppDatabase { //Método para obtener una instancia de la base de datos.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder( //Construye la base de datos usando Room.
                    context.applicationContext, //Evita fugas de memoria.
                    AppDatabase::class.java, //Clase de la base de datos.
                    "smartfit_database" //Nombre del archivo de la base de datos.
                )
                    .fallbackToDestructiveMigration()  // Esto elimina y recrea la base de datos en caso de cambiar el esquema
                    .build() //Finaliza la construcción de la instancia.
                INSTANCE = instance //Guarda la instancia y la devuelve.
                instance
            }
        }
    }
}