package com.edo979.data_remote.source

import com.edo979.data_repository.data_source.UserDataSource
import com.edo979.domain.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MockUserDataSource : UserDataSource {

    override fun getUsers(): Flow<List<User>> = flow {
        delay(1000L)
        emit(
            (1..2).map {
                User(
                    id = it.toLong(),
                    name = "name$it",
                    username = "user$it",
                    email = "email$it"
                )
            }
        )
    }.flowOn(Dispatchers.IO)

    override fun getUser(id: Long): Flow<User> = flow {
        delay(1000L)
        emit(
            User(
                id = id,
                name = "name$id",
                username = "user$id",
                email = "email$id"
            )
        )
    }.flowOn(Dispatchers.IO)
}