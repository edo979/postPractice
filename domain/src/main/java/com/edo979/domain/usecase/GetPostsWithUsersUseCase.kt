package com.edo979.domain.usecase

import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.entity.UseCase
import com.edo979.domain.repository.PostRepository
import com.edo979.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetPostsWithUsersUseCase(
    configuration: Configuration,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : UseCase<GetPostsWithUsersUseCase.Request, GetPostsWithUsersUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> = combine(
        postRepository.getPosts(), userRepository.getUsers()
    ) { posts, users ->
        val postsUsers = posts.map { post ->
            val user = users.first { it.id == post.userId }
            PostWithUser(post, user)
        }
        Response(postsUsers)
    }

    object Request : UseCase.Request
    data class Response(val posts: List<PostWithUser>) : UseCase.Response
}