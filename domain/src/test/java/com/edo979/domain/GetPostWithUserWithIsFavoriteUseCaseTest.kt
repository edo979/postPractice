package com.edo979.domain

import com.edo979.domain.entity.Post
import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.entity.User
import com.edo979.domain.repository.PostRepository
import com.edo979.domain.repository.UserRepository
import com.edo979.domain.usecase.GetPostWithUserWithIsFavoriteUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetPostWithUserWithIsFavoriteUseCaseTest {

    private val postRepository = mock<PostRepository>()
    private val userRepository = mock<UserRepository>()
    private val useCase = GetPostWithUserWithIsFavoriteUseCase(
        mock(),
        postRepository,
        userRepository
    )

    @Test
    fun testProcess() = runTest {
        val id = 1L
        val user1 = User(id, "name1", "username1", "email1")
        val post1 = Post(1L, userId = id, "title1", "body1")

        whenever(postRepository.getPost(id)).thenReturn(flowOf(post1))
        whenever(userRepository.getUser(id)).thenReturn(flowOf(user1))

        val result = useCase.process(GetPostWithUserWithIsFavoriteUseCase.Request(id)).first()

        Assert.assertEquals(
            GetPostWithUserWithIsFavoriteUseCase.Response(PostWithUser(post1, user1)),
            result
        )
    }
}