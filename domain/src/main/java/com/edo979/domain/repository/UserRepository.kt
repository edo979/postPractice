package com.edo979.domain.repository

import com.edo979.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsers(): Flow<List<User>>
    fun getUser(id: Long): Flow<User>
}