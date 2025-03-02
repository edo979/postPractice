package com.edo979.data_repository.data_source.local

import com.edo979.domain.entity.Post
import kotlinx.coroutines.flow.Flow

interface LocalPostDataSource {

    fun getPosts(): Flow<List<Post>>
    fun getPost(id: Long): Flow<Post?>
    fun addPosts(posts: List<Post>)
}