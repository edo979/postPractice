package com.edo979.postpractice.repository

import android.util.Log
import com.edo979.domain.entity.User
import com.edo979.domain.repository.UserRepository
import com.edo979.postpractice.idling.ComposeCountingIdlingResource
import com.edo979.postpractice.idling.attachIdling
import kotlinx.coroutines.flow.Flow

class IdlingRemoteUserRepository(
    private val remoteUserRepository: UserRepository,
    private val countingIdlingResource: ComposeCountingIdlingResource
) : UserRepository {

    override fun getUsers(): Flow<List<User>> =
        remoteUserRepository.getUsers().attachIdling(countingIdlingResource)

    override fun getUser(id: Long): Flow<User> {
        Log.d("CountingIdlingResource", "GET user $id")
        return remoteUserRepository.getUser(id).attachIdling(countingIdlingResource)
    }
}