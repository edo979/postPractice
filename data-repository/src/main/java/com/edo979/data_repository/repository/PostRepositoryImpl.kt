package com.edo979.data_repository.repository

import com.edo979.data_repository.data_source.PostDataSource
import com.edo979.domain.entity.Post
import com.edo979.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class PostRepositoryImpl(private val postDataSource: PostDataSource): PostRepository {

    override fun getPosts(): Flow<List<Post>> = postDataSource.getPosts()

    override fun getPost(id: Long): Flow<Post> = postDataSource.getPost(id)
}