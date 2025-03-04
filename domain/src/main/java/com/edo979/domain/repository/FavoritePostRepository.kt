package com.edo979.domain.repository

import com.edo979.domain.entity.Post
import com.edo979.domain.entity.PostWithUser
import kotlinx.coroutines.flow.Flow

interface FavoritePostRepository {

    fun getPosts(): Flow<List<PostWithUser>>
    fun getPost(id: Long): Flow<PostWithUser?>
    fun addPost(post: PostWithUser): Flow<Unit>
    fun deletePost(post: PostWithUser): Flow<Unit>
}