package com.edo979.data_repository

import com.edo979.data_repository.data_source.local.LocalPostDataSource
import com.edo979.data_repository.repository.FavoritePostRepositoryImpl
import com.edo979.domain.entity.Post
import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.entity.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.internal.matchers.Null
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class FavoritePostRepositoryImplTest {

    private val dataSource = mock<LocalPostDataSource>()
    private val repository = FavoritePostRepositoryImpl(dataSource)
    private val id = 1L
    private val postWithUser = PostWithUser(
        post = Post(
            id = id, userId = 1L, title = "title", body = "body"
        ), user = User(
            id = 1L, name = "name", username = "username", email = "email@email.com"
        )
    )

    @Test
    fun testGetPosts() = runTest {
        whenever(dataSource.getPosts()).thenReturn(flowOf(listOf(postWithUser)))
        val result = repository.getPosts().first()
        Assert.assertEquals(listOf(postWithUser), result)
    }

    @Test
    fun testGetPost() = runTest {
        whenever(dataSource.getPost(id)).thenReturn(flowOf(postWithUser))
        val result = repository.getPost(id).first()
        Assert.assertEquals(postWithUser, result)
    }

    @Test
    fun testPostNotFound() = runTest {
        whenever(dataSource.getPost(id)).thenReturn(flowOf(null))
        val result = repository.getPost(id).first()
        Assert.assertNull(result)
    }

    @Test
    fun testAddPost() = runTest {
        whenever(dataSource.addPost(postWithUser)).thenReturn(Unit)
        val result = repository.addPost(postWithUser).first()
        Assert.assertEquals(Unit, result)
    }

    @Test
    fun testDeletePost() = runTest {
        whenever(dataSource.deletePost(postWithUser)).thenReturn(Unit)
        val result = repository.deletePost(postWithUser).first()
        Assert.assertEquals(Unit, result)
    }
}