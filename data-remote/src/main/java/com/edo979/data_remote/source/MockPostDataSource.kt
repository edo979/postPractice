package com.edo979.data_remote.source

import com.edo979.data_repository.data_source.PostDataSource
import com.edo979.domain.entity.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MockPostDataSource : PostDataSource {

    override fun getPosts(): Flow<List<Post>> = flow {
        delay(2000)
        emit(
            (1..10).map {
                Post(
                    id = it.toLong(),
                    userId = 1L,
                    title = "title$it",
                    body = "body$it"
                )
            })
    }.flowOn(Dispatchers.IO)

    override fun getPost(id: Long): Flow<Post> = flow {
        delay(2000)
        emit(
            Post(
                id = id,
                userId = 1L,
                title = "title$id",
                body = "body1$id"
            )
        )
    }.flowOn(Dispatchers.IO)
}