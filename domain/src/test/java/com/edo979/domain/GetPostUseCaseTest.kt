package com.edo979.domain

import com.edo979.domain.entity.Post
import com.edo979.domain.entity.PostWithData
import com.edo979.domain.entity.User
import com.edo979.domain.repository.FavoritePostRepository
import com.edo979.domain.repository.PostRepository
import com.edo979.domain.repository.UserRepository
import com.edo979.domain.usecase.GetPostUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetPostUseCaseTest {

    private val postRepository = mock<PostRepository>()
    private val userRepository = mock<UserRepository>()
    private val favoritePostRepository = mock<FavoritePostRepository>()
    private val useCase = GetPostUseCase(
        mock(),
        postRepository,
        favoritePostRepository,
        userRepository
    )

    @Test
    fun testProcess() = runTest {
        val id = 1L
        val user1 = User(id, "name1", "username1", "email1")
        val post1 = Post(1L, userId = id, "title1", "body1")

        whenever(postRepository.getPost(id)).thenReturn(flowOf(post1))
        whenever(userRepository.getUser(id)).thenReturn(flowOf(user1))

        whenever(favoritePostRepository.getPost(id)).thenReturn(flowOf(post1))
        val favoritePostResult = useCase.process(GetPostUseCase.Request(id)).first()
        Assert.assertEquals(
            GetPostUseCase.Response(PostWithData(post1, user1, true)),
            favoritePostResult
        )

        whenever(favoritePostRepository.getPost(id)).thenReturn(flowOf(null))
        val result = useCase.process(GetPostUseCase.Request(id)).first()
        Assert.assertEquals(
            GetPostUseCase.Response(PostWithData(post1, user1, false)),
            result
        )
    }
}