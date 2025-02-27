package com.edo979.data_remote

import com.edo979.data_remote.post.PostApiModel
import com.edo979.data_remote.post.PostService
import com.edo979.data_remote.source.RemotePostDataSourceImpl
import com.edo979.domain.entity.Post
import com.edo979.domain.entity.UseCaseException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RemotePostDataSourceImplTest {

    private val postService = mock<PostService>()
    private val postDataSource = RemotePostDataSourceImpl(postService)
    private val postId = 1L
    private val remotePost = PostApiModel(
        id = 1L, userId = 1L, title = "title", body = "body"
    )
    private val expectedPost = Post(
        id = postId, userId = 1L, title = "title", body = "body"
    )

    @Test
    fun testGetPosts() = runTest {
        whenever(postService.getPosts()).thenReturn(listOf(remotePost))

        val result = postDataSource.getPosts().first()
        Assert.assertEquals(listOf(expectedPost), result)
    }

    @Test
    fun testGetPost() = runTest {
        whenever(postService.getPost(postId)).thenReturn(remotePost)

        val result = postDataSource.getPost(postId).first()
        Assert.assertEquals(expectedPost, result)
    }

    @Test
    fun testGetPostsThrowError() = runTest {
        whenever(postService.getPosts()).thenThrow(RuntimeException())

        postDataSource.getPosts().catch {
            Assert.assertTrue(it is UseCaseException.PostException)
        }.collect()
    }

    @Test
    fun testGetPostThrowError() = runTest {
        whenever(postService.getPost(postId)).thenThrow(RuntimeException())

        postDataSource.getPost(postId).catch {
            Assert.assertTrue(it is UseCaseException.PostException)
        }.collect()
    }
}