package com.edo979.domain.usecase

import com.edo979.domain.entity.PostWithData
import com.edo979.domain.repository.FavoritePostRepository
import com.edo979.domain.repository.PostRepository
import com.edo979.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class GetPostUseCase(
    configuration: Configuration,
    private val postRepository: PostRepository,
    private val favoritePostRepository: FavoritePostRepository,
    private val userRepository: UserRepository
) : UseCase<GetPostUseCase.Request, GetPostUseCase.Response>(
    configuration
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun process(request: Request): Flow<Response> =
        favoritePostRepository.getPost(request.postId).flatMapLatest { favoritePost ->
            when (favoritePost) {
                null -> postRepository.getPost(request.postId).map { remotePost ->
                    Response(
                        PostWithData(
                            post = remotePost,
                            user = userRepository.getUser(remotePost.userId).first(),
                            isFavorite = false
                        )
                    )
                }

                else -> flowOf(
                    Response(
                        PostWithData(
                            favoritePost.post,
                            favoritePost.user,
                            isFavorite = true
                        )
                    )
                )
            }
        }

    data class Request(val postId: Long) : UseCase.Request
    data class Response(val post: PostWithData) : UseCase.Response
}