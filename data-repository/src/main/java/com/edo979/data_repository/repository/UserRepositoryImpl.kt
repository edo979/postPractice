package com.edo979.data_repository.repository

import com.edo979.data_repository.data_source.remote.RemoteUserDataSource
import com.edo979.domain.entity.User
import com.edo979.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val remoteUserDataSource: RemoteUserDataSource) : UserRepository {

    override fun getUsers(): Flow<List<User>> = remoteUserDataSource.getUsers()

    override fun getUser(id: Long): Flow<User> = remoteUserDataSource.getUser(id)
}