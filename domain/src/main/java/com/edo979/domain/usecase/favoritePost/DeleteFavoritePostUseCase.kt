package com.edo979.domain.usecase.favoritePost

import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.entity.UseCase
import com.edo979.domain.repository.FavoritePostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeleteFavoritePostUseCase(
    configuration: Configuration,
    private val favoritePostRepository: FavoritePostRepository
) :
    UseCase<DeleteFavoritePostUseCase.Request, DeleteFavoritePostUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        favoritePostRepository.deletePost(request.post).map {
            Response
        }

    data class Request(val post: PostWithUser) : UseCase.Request
    object Response : UseCase.Response
}