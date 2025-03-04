package com.edo979.domain

import com.edo979.domain.entity.Post
import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.entity.User
import com.edo979.domain.repository.FavoritePostRepository
import com.edo979.domain.usecase.favoritePost.SaveFavoritePostUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SaveFavoritePostUseCaseTest {

    private val favoritePostRepository = mock<FavoritePostRepository>()
    private val useCase = SaveFavoritePostUseCase(mock(), favoritePostRepository)

    @Test
    fun testProcess() = runTest {
        val postWithUser = PostWithUser(
            post = Post(
                id = 1L, userId = 1L, title = "title", body = "body"
            ), user = User(
                id = 1L, name = "name", username = "username", email = "email@email.com"
            )
        )
        whenever(favoritePostRepository.addPost(postWithUser)).thenReturn(flowOf(Unit))

        val result = useCase.process(SaveFavoritePostUseCase.Request(postWithUser)).first()

        Assert.assertEquals(SaveFavoritePostUseCase.Response, result)
    }
}