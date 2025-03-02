package com.edo979.domain.usecase

import com.edo979.domain.entity.PostWithData
import com.edo979.domain.entity.UseCase
import com.edo979.domain.repository.PostRepository
import com.edo979.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class GetPostWithUserWithIsFavoriteUseCase(
    configuration: Configuration,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : UseCase<GetPostWithUserWithIsFavoriteUseCase.Request, GetPostWithUserWithIsFavoriteUseCase.Response>(
    configuration
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun process(request: Request): Flow<Response> =
        postRepository.getPost(request.postId).flatMapLatest { post ->
            userRepository.getUser(post.userId).map { user ->
                Response(PostWithData(post, user))
            }
        }

    data class Request(val postId: Long) : UseCase.Request
    data class Response(val postWithData: PostWithData) : UseCase.Response
}