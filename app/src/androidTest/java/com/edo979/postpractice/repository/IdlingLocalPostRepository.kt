package com.edo979.postpractice.repository

import android.util.Log
import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.repository.FavoritePostRepository
import com.edo979.postpractice.idling.ComposeCountingIdlingResource
import com.edo979.postpractice.idling.attachIdling
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class IdlingLocalPostRepository(
    private val localPostRepository: FavoritePostRepository,
    private val countingIdlingResource: ComposeCountingIdlingResource
) : FavoritePostRepository {

    override fun getPosts(): Flow<List<PostWithUser>> =
        localPostRepository.getPosts().attachIdling(countingIdlingResource)

    @OptIn(FlowPreview::class)
    override fun getPost(id: Long): Flow<PostWithUser?> {
        return localPostRepository.getPost(id).onEach {
            Log.d("CountingIdlingResource", "GET post $it")
        }.attachIdling(countingIdlingResource)
    }


    override fun addPost(post: PostWithUser): Flow<Unit> {
        Log.d("CountingIdlingResource", "addPost")
        return localPostRepository.addPost(post).attachIdling(countingIdlingResource)
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