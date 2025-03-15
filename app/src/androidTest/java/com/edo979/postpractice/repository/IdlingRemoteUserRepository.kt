package com.edo979.postpractice.repository

import com.edo979.domain.entity.User
import com.edo979.domain.repository.UserRepository
import com.edo979.postpractice.idling.ComposeCountingIdlingResource
import com.edo979.postpractice.idling.attachIdling
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

class IdlingRemoteUserRepository(
    private val remoteUserRepository: UserRepository,
    private val countingIdlingResource: ComposeCountingIdlingResource
) : UserRepository {
    lateinit var user: User

    override fun getUsers(): Flow<List<User>> =
        remoteUserRepository.getUsers().attachIdling(countingIdlingResource)

    override fun getUser(id: Long): Flow<User> {
        runBlocking {
            remoteUserRepository.getUser(id).attachIdling(countingIdlingResource).collect {
                user = it
            }
        }
        return flowOf(user)
    }
}