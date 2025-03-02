package com.edo979.data_repository

import com.edo979.data_repository.data_source.remote.RemotePostDataSource
import com.edo979.data_repository.repository.PostRepositoryImpl
import com.edo979.domain.entity.Post
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PostRepositoryImplTest {

    private val remotePostDataSource = mock<RemotePostDataSource>()
    private val postRepository = PostRepositoryImpl(remotePostDataSource)

    @Test
    fun testGetPosts() = runTest {
        val posts = listOf(Post(1, 1, "Title", "body"))
        whenever(remotePostDataSource.getPosts()).thenReturn(flowOf(posts))

        val result = postRepository.getPosts().first()
        Assert.assertEquals(result, posts)
    }

    @Test
    fun testGetPost() = runTest {
        val id = 1L
        val post = Post(1, userId = id, "Title", "body")
        whenever(remotePostDataSource.getPost(id)).thenReturn(flowOf(post))

        val result = postRepository.getPost(id).first()
        Assert.assertEquals(result, post)
    }
}