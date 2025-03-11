package com.edo979.postpractice.repository

import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.repository.FavoritePostRepository
import com.edo979.postpractice.idling.ComposeCountingIdlingResource
import com.edo979.postpractice.idling.attachIdling
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class IdlingLocalPostRepository(
    private val localPostRepository: FavoritePostRepository,
    private val countingIdlingResource: ComposeCountingIdlingResource
) : FavoritePostRepository {

    override fun getPosts(): Flow<List<PostWithUser>> =
        localPostRepository.getPosts().attachIdling(countingIdlingResource)

    override fun getPost(id: Long): Flow<PostWithUser?> =
        localPostRepository.getPost(id).attachIdling(countingIdlingResource)

    override fun addPost(post: PostWithUser): Flow<Unit> = flow {
        try {
            countingIdlingResource.increment()
            localPostRepository.addPost(post)
            emit(Unit)
        } finally {
            countingIdlingResource.decrement()
        }
    }

    override fun deletePost(post: PostWithUser): Flow<Unit> = flow {
        try {
            countingIdlingResource.increment()
            localPostRepository.deletePost(post)
            emit(Unit)
        } finally {
            countingIdlingResource.decrement()
        }
    }
}