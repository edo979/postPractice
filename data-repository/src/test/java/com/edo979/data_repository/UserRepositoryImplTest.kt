package com.edo979.data_repository

import com.edo979.data_repository.data_source.UserDataSource
import com.edo979.data_repository.repository.UserRepositoryImpl
import com.edo979.domain.entity.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class UserRepositoryImplTest {

    private val userDataSource = mock<UserDataSource>()
    private val userRepository = UserRepositoryImpl(userDataSource)

    @Test
    fun testGetUsers() = runTest {
        val users = listOf(
            User(
                id = 1L, name = "name", username = "username", email = "email"
            )
        )
        whenever(userDataSource.getUsers()).thenReturn(flowOf(users))

        val result = userRepository.getUsers().first()
        Assert.assertEquals(result, users)
    }

    @Test
    fun testGetUser() = runTest {
        val id = 1L
        val user = User(
            id = id, name = "name", username = "username", email = "email"
        )
        whenever(userDataSource.getUser(id)).thenReturn(flowOf(user))

        val result = userRepository.getUser(id).first()
        Assert.assertEquals(result, user)
    }
}