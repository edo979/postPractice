package com.edo979.data_repository.data_source.local

import com.edo979.domain.entity.PostWithUser
import kotlinx.coroutines.flow.Flow

interface LocalPostDataSource {

    fun getPosts(): Flow<List<PostWithUser>>
    fun getPost(id: Long): Flow<PostWithUser?>
    suspend fun addPost(post: PostWithUser)
    suspend fun deletePost(post: PostWithUser)
}