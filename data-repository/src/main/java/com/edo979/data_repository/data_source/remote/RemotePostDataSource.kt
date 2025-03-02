package com.edo979.data_repository.data_source.remote

import com.edo979.domain.entity.Post
import kotlinx.coroutines.flow.Flow

interface RemotePostDataSource {

    fun getPosts(): Flow<List<Post>>
    fun getPost(id: Long): Flow<Post>
}