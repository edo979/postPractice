package com.edo979.data_local.sources

import com.edo979.data_local.db.post.PostDao
import com.edo979.data_local.db.post.PostEntity
import com.edo979.data_repository.data_source.local.LocalPostDataSource
import com.edo979.domain.entity.Post
import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalPostDataSourceImpl @Inject constructor(private val postDao: PostDao) :
    LocalPostDataSource {

    override fun getPosts(): Flow<List<PostWithUser>> =
        postDao.getPosts().map { posts -> posts.map(::toDomainEntity) }

    override fun getPost(id: Long): Flow<PostWithUser?> = flow {
        val postEntity = postDao.getPost(id)
        val post = postEntity?.let(::toDomainEntity)

        emit(post)
    }.flowOn(Dispatchers.IO)

    override suspend fun addPost(post: PostWithUser) = withContext(Dispatchers.IO) {
        val postEntity = toDataEntity(post)
        postDao.addPost(postEntity)
    }

    override suspend fun deletePost(post: PostWithUser) = withContext(Dispatchers.IO) {
        val postEntity = toDataEntity(post)
        postDao.deletePost(postEntity)
    }

    private fun toDomainEntity(entity: PostEntity) = PostWithUser(
        post = Post(
            id = entity.id,
            userId = entity.userId,
            title = entity.title,
            body = entity.body,
        ),
        user = User(
            id = entity.userId,
            name = entity.authorName,
            username = entity.authorUsername,
            email = entity.authorEmail
        ),
    )

    private fun toDataEntity(postWithUser: PostWithUser) = PostEntity(
        id = postWithUser.post.id,
        title = postWithUser.post.title,
        body = postWithUser.post.body,
        userId = postWithUser.post.userId,
        authorName = postWithUser.user.name,
        authorUsername = postWithUser.user.username,
        authorEmail = postWithUser.user.email
    )
}