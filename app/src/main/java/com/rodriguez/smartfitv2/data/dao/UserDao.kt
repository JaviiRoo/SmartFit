package com.rodriguez.smartfitv2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.rodriguez.smartfitv2.data.model.User

//Marca esta interfaz como un DAO (Data Access Object) de Room. Aquí defines todas las operaciones que Room puede realizar sobre la tabla users.
@Dao
interface UserDao {

    //Inserta un usuario en la base de datos.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    //Recupera un usuario que coincida con un email y contraseña específicos.
    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun getUserByCredentials(email: String, password: String): User?

    //Actualiza un usuario ya existente.
    @Update
    suspend fun updateUser(user: User)

    //Elimina un usuario ya existente.
    @Delete
    suspend fun deleteUser(user: User)

    //Recupera un usuario por su correo electrónico.
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
}