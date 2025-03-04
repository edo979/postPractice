package com.edo979.domain

import com.edo979.domain.entity.Post
import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.entity.User
import com.edo979.domain.repository.FavoritePostRepository
import com.edo979.domain.usecase.favoritePost.DeleteFavoritePostUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class DeleteFavoritePostUseCaseTest {

    private val favoritePostRepository = mock<FavoritePostRepository>()
    private val useCase = DeleteFavoritePostUseCase(mock(), favoritePostRepository)

    @Test
    fun testProcess() = runTest {
        val postWithUser = PostWithUser(
            post = Post(
                id = 1L, userId = 1L, title = "title", body = "body"
            ), user = User(
                id = 1L, name = "name", username = "username", email = "email@email.com"
            )
        )
        whenever(favoritePostRepository.deletePost(postWithUser)).thenReturn(flowOf(Unit))

        val result = useCase.process(DeleteFavoritePostUseCase.Request(postWithUser)).first()

        Assert.assertEquals(DeleteFavoritePostUseCase.Response, result)
    }
}