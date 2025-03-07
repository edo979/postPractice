package com.edo979.data_remote.source

import com.edo979.data_remote.post.PostApiModel
import com.edo979.data_remote.post.PostService
import com.edo979.data_repository.data_source.remote.RemotePostDataSource
import com.edo979.domain.entity.Post
import com.edo979.domain.entity.UseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemotePostDataSourceImpl @Inject constructor(private val postService: PostService) :
    RemotePostDataSource {

    override fun getPosts(): Flow<List<Post>> = flow { emit(postService.getPosts()) }
        .map { posts -> posts.map(::convert) }
        .catch { throw UseCaseException.PostException(it) }

    override fun getPost(id: Long): Flow<Post> = flow { emit(postService.getPost(id)) }
        .map(::convert)
        .catch { throw UseCaseException.PostException(it) }

    private fun convert(postApiModel: PostApiModel) = Post(
        id = postApiModel.id,
        userId = postApiModel.userId,
        title = postApiModel.title,
        body = postApiModel.body
    )
}