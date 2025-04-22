package com.rodriguez.smartfitv2.data.repository

import com.rodriguez.smartfitv2.data.dao.UserDao
import com.rodriguez.smartfitv2.data.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun loginUser(email: String, password: String): User? {
        return userDao.getUserByCredentials(email, password)
    }
}
