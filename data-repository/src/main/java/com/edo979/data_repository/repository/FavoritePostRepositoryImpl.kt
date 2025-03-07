package com.edo979.data_repository.repository

import com.edo979.data_repository.data_source.local.LocalPostDataSource
import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.repository.FavoritePostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoritePostRepositoryImpl(private val localPostDataSource: LocalPostDataSource) :
    FavoritePostRepository {

    override fun getPosts(): Flow<List<PostWithUser>> = localPostDataSource.getPosts()

    override fun getPost(id: Long): Flow<PostWithUser?> = localPostDataSource.getPost(id)

    override fun addPost(post: PostWithUser): Flow<Unit> = flow {
        localPostDataSource.addPost(post)
        this.emit(Unit)
    }

    override fun deletePost(post: PostWithUser): Flow<Unit> = flow {
        localPostDataSource.deletePost(post)
        this.emit(Unit)
    }
}