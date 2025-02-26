package com.edo979.domain.repository

import com.edo979.domain.entity.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun getPosts(): Flow<List<Post>>
    fun getPost(id: Long): Flow<Post>
}