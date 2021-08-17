package com.dev.eloper.mud.service

import java.util.*

object UserService {
    private val USER_MAP = mutableMapOf<String, User>()

    fun createUser(name: String): User {
        val user = User(name, UUID.randomUUID().toString())
        USER_MAP[user.userId] = user
        return user
    }

    fun getUser(userId: String): User? = USER_MAP[userId]
}

data class User(val name: String, val userId: String)