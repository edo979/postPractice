package com.edo979.data_remote

import com.edo979.data_remote.source.RemoteUserDataSourceImpl
import com.edo979.data_remote.user.UserApiModel
import com.edo979.data_remote.user.UserService
import com.edo979.domain.entity.UseCaseException
import com.edo979.domain.entity.User
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RemoteUserDataSourceImplTest {

    private val userService = mock<UserService>()
    private val userDataSource = RemoteUserDataSourceImpl(userService)
    private val userId = 1L
    private val remoteUser = UserApiModel(
        1L,
        name = "name",
        username = "username",
        email = "email",
    )
    private val expectedUser = User(
        id = userId, name = "name", username = "username", email = "email"
    )

    @Test
    fun testGetUsers() = runTest {
        val remoteUsers = listOf(remoteUser)
        val expectedUsers = listOf(expectedUser)
        whenever(userService.getUsers()).thenReturn(remoteUsers)

        val result = userDataSource.getUsers().first()
        Assert.assertEquals(expectedUsers, result)
    }

    @Test
    fun testGetUser() = runTest {
        whenever(userService.getUser(userId)).thenReturn(remoteUser)

        val result = userDataSource.getUser(userId).first()
        Assert.assertEquals(result, expectedUser)
    }

    @Test
    fun getUsersThrowError() = runTest {
        whenever(userService.getUsers()).thenThrow(RuntimeException())

        val result = userDataSource.getUsers().catch {
            Assert.assertTrue(it is UseCaseException.UserException)
        }.collect()
    }

    @Test
    fun getUserThrowError() = runTest {
        whenever(userService.getUser(userId)).thenThrow(RuntimeException())

        val result = userDataSource.getUser(userId).catch {
            Assert.assertTrue(it is UseCaseException.UserException)
        }.collect()
    }
}