package com.edo979.data_repository

import com.edo979.data_repository.data_source.local.LocalPostDataSource
import com.edo979.data_repository.repository.FavoritePostRepositoryImpl
import com.edo979.domain.entity.Post
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class FavoritePostRepositoryImplTest {

    private val dataSource = mock<LocalPostDataSource>()
    private val repository = FavoritePostRepositoryImpl(dataSource)
    private val id = 1L
    private val post = Post(id = id, userId = id, "Title", "body")

    @Test
    fun testGetPosts() = runTest {
        whenever(dataSource.getPosts()).thenReturn(flowOf(listOf(post)))
        val result = repository.getPosts().first()
        Assert.assertEquals(listOf(post), result)
    }

    @Test
    fun testGetPost() = runTest {
        whenever(dataSource.getPost(id)).thenReturn(flowOf(post))
        val result = repository.getPost(id).first()
        Assert.assertEquals(post, result)
    }

    @Test
    fun testAddPost() = runTest {
        whenever(dataSource.addPost(post)).thenReturn(Unit)
        val result = repository.addPost(post).first()
        Assert.assertEquals(Unit, result)
    }

    @Test
    fun testDeletePost() = runTest {
        whenever(dataSource.deletePost(post)).thenReturn(Unit)
        val result = repository.deletePost(post).first()
        Assert.assertEquals(Unit, result)
    }
}