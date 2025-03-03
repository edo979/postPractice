package com.edo979.domain

import com.edo979.domain.entity.Post
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
        val post = Post(
            id = 1L, userId = 1L, title = "Title", body = "Body"
        )
        whenever(favoritePostRepository.addPost(post)).thenReturn(flowOf(Unit))

        val result = useCase.process(SaveFavoritePostUseCase.Request(post)).first()

        Assert.assertEquals(SaveFavoritePostUseCase.Response, result)
    }
}