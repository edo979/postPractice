package com.edo979.data_local.sources

import com.edo979.data_local.db.post.PostDao
import com.edo979.data_local.db.post.PostEntity
import com.edo979.data_repository.data_source.local.LocalPostDataSource
import com.edo979.domain.entity.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalPostDataSourceImpl @Inject constructor(private val postDao: PostDao) :
    LocalPostDataSource {

    override fun getPosts(): Flow<List<Post>> =
        postDao.getPosts().map { posts -> posts.map(::toDomainEntity) }

    override fun getPost(id: Long): Flow<Post?> = flow {
        withContext(Dispatchers.IO) {
            val postEntity = postDao.getPost(id)
            val post = postEntity?.let(::toDomainEntity)

            emit(post)
        }
    }

    override suspend fun addPost(post: Post) = withContext(Dispatchers.IO) {

    }

    override suspend fun deletePost(post: Post) {
        TODO("Not yet implemented")
    }

    private fun toDomainEntity(entity: PostEntity) = Post(
        id = entity.id,
        userId = entity.userId,
        title = entity.title,
        body = entity.body,
    )

    private fun toDataEntity(post: Post) = PostEntity(
        id = post.id,
        title = post.title,
        body = post.body,
        userId = post.userId,
        author = "TODO()",
        authorEmail = "TODO()"
    )
}