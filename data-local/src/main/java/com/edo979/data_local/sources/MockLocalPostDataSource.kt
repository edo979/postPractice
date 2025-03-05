package com.edo979.data_local.sources

import com.edo979.data_repository.data_source.local.LocalPostDataSource
import com.edo979.domain.entity.Post
import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MockLocalPostDataSource @Inject constructor() : LocalPostDataSource {

    private fun getUserId(postId: Int): Long = postId.toLong()

    override fun getPosts(): Flow<List<PostWithUser>> = flow {
        emit(
            (1..10).map {
                PostWithUser(
                    post = Post(
                        id = it.toLong(),
                        userId = getUserId(it),
                        title = "title$it",
                        body = "body$it"
                    ),
                    user = User(
                        id = getUserId(it),
                        name = "name$it",
                        username = "username$it",
                        email = "email$it"
                    )
                )
            }
        )
    }.flowOn(Dispatchers.IO)

    override fun getPost(id: Long): Flow<PostWithUser?> = flow {
        emit(
            PostWithUser(
                post = Post(
                    id = id,
                    userId = getUserId(id.toInt()),
                    title = "title$id",
                    body = "body$id"
                ),
                user = User(
                    id = getUserId(id.toInt()),
                    name = "name$id",
                    username = "username$id",
                    email = "email$id"
                )
            )
        )
    }.flowOn(Dispatchers.IO)

    override suspend fun addPost(post: PostWithUser) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePost(post: PostWithUser) {
        TODO("Not yet implemented")
    }
}