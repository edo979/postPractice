package com.edo979.domain.repository

import com.edo979.domain.entity.Post
import kotlinx.coroutines.flow.Flow

interface FavoritePostRepository {

    fun getPosts(): Flow<List<Post>>
    fun getPost(id: Long): Flow<Post>
    suspend fun addPost(post: Post)
}