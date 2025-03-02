package com.edo979.domain

import com.edo979.domain.entity.Post
import com.edo979.domain.entity.PostWithData
import com.edo979.domain.entity.User
import com.edo979.domain.repository.PostRepository
import com.edo979.domain.repository.UserRepository
import com.edo979.domain.usecase.GetPostsWithUsersUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetPostsWithUsersUseCaseTest {

    private val postRepository = mock<PostRepository>()
    private val userRepository = mock<UserRepository>()
    private val useCase = GetPostsWithUsersUseCase(
        mock(),
        postRepository,
        userRepository
    )

    @Test
    fun testProcess() = runTest {
        val user1 = User(1L, "name1", "username1", "email1")
        val user2 = User(2L, "name2", "username2", "email2")
        val post1 = Post(1L, user1.id, "title1", "body1")
        val post2 = Post(2L, user1.id, "title2", "body2")
        val post3 = Post(3L, user2.id, "title3", "body3")
        val post4 = Post(4L, user2.id, "title4", "body4")

        whenever(userRepository.getUsers()).thenReturn(flowOf(listOf(user1, user2)))
        whenever(postRepository.getPosts()).thenReturn(flowOf(listOf(post1, post2, post3, post4)))

        val response = useCase.process(GetPostsWithUsersUseCase.Request).first()

        Assert.assertEquals(
            GetPostsWithUsersUseCase.Response(
                listOf(
                    PostWithData(post1, user1),
                    PostWithData(post2, user1),
                    PostWithData(post3, user2),
                    PostWithData(post4, user2),
                )
            ), response
        )
    }
}