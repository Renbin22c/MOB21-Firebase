package com.renbin.mob21firebases.data.repo

import com.renbin.mob21firebases.data.model.User


interface UserRepo {
    suspend fun addUser(id: String, user:User)
    suspend fun getUser(id: String): User?
    suspend fun updateUser(id: String, user: User)
}