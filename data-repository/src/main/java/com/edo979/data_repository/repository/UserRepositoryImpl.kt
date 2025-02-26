package com.edo979.data_repository.repository

import com.edo979.data_repository.data_source.UserDataSource
import com.edo979.domain.entity.User
import com.edo979.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val userDataSource: UserDataSource) : UserRepository {

    override fun getUsers(): Flow<List<User>> = userDataSource.getUsers()

    override fun getUser(id: Long): Flow<User> = userDataSource.getUser(id)
}