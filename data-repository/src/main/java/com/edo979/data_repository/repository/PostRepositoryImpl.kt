package com.edo979.data_repository.repository

import com.edo979.data_repository.data_source.remote.RemotePostDataSource
import com.edo979.domain.entity.Post
import com.edo979.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class PostRepositoryImpl(private val remotePostDataSource: RemotePostDataSource): PostRepository {

    override fun getPosts(): Flow<List<Post>> = remotePostDataSource.getPosts()

    override fun getPost(id: Long): Flow<Post> = remotePostDataSource.getPost(id)
}