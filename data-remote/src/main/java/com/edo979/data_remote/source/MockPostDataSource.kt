package com.edo979.data_remote.source

import com.edo979.data_repository.data_source.PostDataSource
import com.edo979.domain.entity.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MockPostDataSource @Inject constructor() : PostDataSource {

    override fun getPosts(): Flow<List<Post>> = flow {
        delay(2000)
        emit(
            (1..10).map {
                Post(
                    id = it.toLong(),
                    userId = getUserId(it),
                    title = "title$it",
                    body = "body$it"
                )
            })
    }.flowOn(Dispatchers.IO)

    private fun getUserId(postId: Int) = if (postId < 5) 1L else 2L

    override fun getPost(id: Long): Flow<Post> = flow {
        delay(2000)
        emit(
            Post(
                id = id,
                userId = getUserId(id.toInt()),
                title = "title$id",
                body = "body1$id"
            )
        )
    }.flowOn(Dispatchers.IO)
}