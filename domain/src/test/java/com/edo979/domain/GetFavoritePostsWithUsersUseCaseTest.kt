package com.edo979.domain

import com.edo979.domain.entity.Post
import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.entity.User
import com.edo979.domain.repository.FavoritePostRepository
import com.edo979.domain.usecase.favoritePost.GetFavoritePostsWithUsersUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetFavoritePostsWithUsersUseCaseTest {

    private val repository = mock<FavoritePostRepository>()
    private val useCase = GetFavoritePostsWithUsersUseCase(
        mock(),
        repository
    )
    private val posts = listOf(
        PostWithUser(
            Post(1, 1, "title", "body"),
            User(1, "name", "username", "email")
        )
    )

    @Test
    fun testProcess() = runTest {
        // Given
        whenever(repository.getPosts()).thenReturn(flowOf(posts))
        // When
        val result = useCase.process(GetFavoritePostsWithUsersUseCase.Request).first()
        // Then
        Assert.assertEquals(GetFavoritePostsWithUsersUseCase.Response(posts), result)
    }
}