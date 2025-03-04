package com.edo979.data_local

import com.edo979.data_local.db.post.PostDao
import com.edo979.data_local.db.post.PostEntity
import com.edo979.data_local.sources.LocalPostDataSourceImpl
import com.edo979.domain.entity.Post
import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.entity.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LocalPostDataSourceImplTest {

    private val postDao = mock<PostDao>()
    private val localDataSource = LocalPostDataSourceImpl(postDao)
    private val id = 1L
    private val postWithUser = PostWithUser(
        post = Post(
            id = id, userId = 1L, title = "title", body = "body"
        ), user = User(
            id = 1L, name = "name", username = "username", email = "email"
        )
    )
    private val postEntity = PostEntity(
        id = id,
        title = "title",
        body = "body",
        userId = 1L,
        authorName = "name",
        authorUsername = "username",
        authorEmail = "email"
    )

    @Test
    fun testGetPosts() = runTest {
        whenever(postDao.getPosts()).thenReturn(flowOf(listOf(postEntity)))
        val result = localDataSource.getPosts().first()
        Assert.assertEquals(listOf(postWithUser), result)
    }

    @Test
    fun testGetPostsWhenNoPosts() = runTest {
        whenever(postDao.getPosts()).thenReturn(flowOf(emptyList()))
        val result = localDataSource.getPosts().first()
        Assert.assertEquals(emptyList<PostWithUser>(), result)
    }

    @Test
    fun testGetPost() = runTest {
        whenever(postDao.getPost(id)).thenReturn(postEntity)
        val result = localDataSource.getPost(id).first()
        Assert.assertEquals(postWithUser, result)
    }

    @Test
    fun testGetPostWhenNoPost() = runTest {
        whenever(postDao.getPost(id)).thenReturn(null)
        val result = localDataSource.getPost(id).first()
        Assert.assertEquals(null, result)
    }

    @Test
    fun testAddPost() = runTest {
        localDataSource.addPost(postWithUser)
        verify(postDao).addPost(postEntity)
    }

    @Test
    fun testDeletePost() = runTest {
        localDataSource.deletePost(postWithUser)
        verify(postDao).deletePost(postEntity)
    }
}