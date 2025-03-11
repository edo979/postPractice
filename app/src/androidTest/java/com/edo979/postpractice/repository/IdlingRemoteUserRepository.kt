package com.edo979.postpractice.repository

import com.edo979.domain.entity.User
import com.edo979.domain.repository.UserRepository
import com.edo979.postpractice.idling.ComposeCountingIdlingResource
import com.edo979.postpractice.idling.attachIdling
import kotlinx.coroutines.flow.Flow

class IdlingRemoteUser(
    private val remoteUserRepository: UserRepository,
    private val countingIdlingResource: ComposeCountingIdlingResource
) : UserRepository {

    override fun getUsers(): Flow<List<User>> =
        remoteUserRepository.getUsers().attachIdling(countingIdlingResource)

    override fun getUser(id: Long): Flow<User> =
        remoteUserRepository.getUser(id).attachIdling(countingIdlingResource)
}