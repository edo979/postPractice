package com.edo979.data_repository.data_source.remote

import com.edo979.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface RemoteUserDataSource {

    fun getUsers(): Flow<List<User>>
    fun getUser(id: Long): Flow<User>
}