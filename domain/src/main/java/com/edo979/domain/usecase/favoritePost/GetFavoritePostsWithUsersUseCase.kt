package com.edo979.domain.usecase.favoritePost

import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.repository.FavoritePostRepository
import com.edo979.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFavoritePostsWithUsersUseCase(
    configuration: Configuration,
    private val repository: FavoritePostRepository
) :
    UseCase<GetFavoritePostsWithUsersUseCase.Request, GetFavoritePostsWithUsersUseCase.Response>(
        configuration
    ) {

    override fun process(request: Request): Flow<Response> =
        repository.getPosts().map { Response(it) }

    object Request : UseCase.Request
    data class Response(val posts: List<PostWithUser>) : UseCase.Response
}