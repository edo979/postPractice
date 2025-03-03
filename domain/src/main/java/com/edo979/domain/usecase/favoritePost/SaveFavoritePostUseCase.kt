package com.edo979.domain.usecase.favoritePost

import com.edo979.domain.entity.Post
import com.edo979.domain.entity.UseCase
import com.edo979.domain.repository.FavoritePostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SaveFavoritePostUseCase(
    configuration: Configuration,
    private val favoritePostRepository: FavoritePostRepository
) :
    UseCase<SaveFavoritePostUseCase.Request, SaveFavoritePostUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        favoritePostRepository.addPost(request.post).map { Response }

    data class Request(val post: Post) : UseCase.Request
    object Response : UseCase.Response
}