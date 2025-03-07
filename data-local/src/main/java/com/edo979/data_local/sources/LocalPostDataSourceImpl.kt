package com.edo979.data_local.sources

import com.edo979.data_local.db.post.PostDao
import com.edo979.data_local.db.post.PostEntity
import com.edo979.data_repository.data_source.local.LocalPostDataSource
import com.edo979.domain.entity.Post
import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.entity.UseCaseException
import com.edo979.domain.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalPostDataSourceImpl @Inject constructor(private val postDao: PostDao) :
    LocalPostDataSource {

    override fun getPosts(): Flow<List<PostWithUser>> =
        postDao.getPosts()
            .map { posts -> posts.map(::toDomainEntity) }
            .catch { throw UseCaseException.LocalPostException(it) }

    override fun getPost(id: Long): Flow<PostWithUser?> =
        postDao.getPost(id)
            .map { it?.let(::toDomainEntity) }
            .flowOn(Dispatchers.IO)
            .catch { throw UseCaseException.LocalPostException(it) }

    override suspend fun addPost(post: PostWithUser) = try {
        val postEntity = toDataEntity(post)
        postDao.addPost(postEntity)
    } catch (e: Exception) {
        throw UseCaseException.LocalPostException(e)
    }

    override suspend fun deletePost(post: PostWithUser) = try {
        val postEntity = toDataEntity(post)
        postDao.deletePost(postEntity)
    } catch (e: Exception) {
        throw UseCaseException.LocalPostException(e)
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