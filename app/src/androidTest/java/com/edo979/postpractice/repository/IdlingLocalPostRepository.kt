package com.edo979.postpractice.repository

import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.repository.FavoritePostRepository
import com.edo979.postpractice.idling.ComposeCountingIdlingResource
import com.edo979.postpractice.idling.attachIdling
import kotlinx.coroutines.flow.Flow

class IdlingLocalPostRepository(
    private val localPostRepository: FavoritePostRepository,
    private val countingIdlingResource: ComposeCountingIdlingResource
) : FavoritePostRepository {

    override fun getPosts(): Flow<List<PostWithUser>> =
        localPostRepository.getPosts().attachIdling(countingIdlingResource)

    override fun getPost(id: Long): Flow<PostWithUser?> =
        localPostRepository.getPost(id).attachIdling(countingIdlingResource)

    override fun addPost(post: PostWithUser): Flow<Unit> =
        localPostRepository.addPost(post).attachIdling(countingIdlingResource)

    override fun deletePost(post: PostWithUser): Flow<Unit> =
        localPostRepository.deletePost(post).attachIdling(countingIdlingResource)

}