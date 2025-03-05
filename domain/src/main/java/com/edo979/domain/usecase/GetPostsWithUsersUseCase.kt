package com.edo979.domain.usecase

import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.repository.FavoritePostRepository
import com.edo979.domain.repository.PostRepository
import com.edo979.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetPostsWithUsersUseCase(
    configuration: Configuration,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val favoritePostRepository: FavoritePostRepository
) : UseCase<GetPostsWithUsersUseCase.Request, GetPostsWithUsersUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> = combine(
        postRepository.getPosts(), userRepository.getUsers(), favoritePostRepository.getPosts()
    ) { posts, users, favoritePosts ->
        val postsUsers = posts.map { post ->
            val user = users.first { it.id == post.userId }
            PostWithUser(post, user)
        }

        Response(postsUsers, favoritePosts)
    }

    object Request : UseCase.Request
    data class Response(val posts: List<PostWithUser>, val favoritePosts: List<PostWithUser>) :
        UseCase.Response
}