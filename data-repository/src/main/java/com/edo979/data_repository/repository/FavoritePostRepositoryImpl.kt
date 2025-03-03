package com.edo979.data_repository.repository

import com.edo979.data_repository.data_source.local.LocalPostDataSource
import com.edo979.domain.entity.Post
import com.edo979.domain.repository.FavoritePostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoritePostRepositoryImpl(private val localPostDataSource: LocalPostDataSource) :
    FavoritePostRepository {

    override fun getPosts(): Flow<List<Post>> = localPostDataSource.getPosts()

    override fun getPost(id: Long): Flow<Post?> = localPostDataSource.getPost(id)

    override fun addPost(post: Post): Flow<Unit> = flow {
        localPostDataSource.addPost(post)
        this.emit(Unit)
    }

    override fun deletePost(post: Post): Flow<Unit> = flow {
        localPostDataSource.deletePost(post)
        this.emit(Unit)
    }
}