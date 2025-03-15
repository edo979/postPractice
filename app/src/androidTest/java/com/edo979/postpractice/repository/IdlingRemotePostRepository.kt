package com.edo979.postpractice.repository

import android.util.Log
import com.edo979.domain.entity.Post
import com.edo979.domain.repository.PostRepository
import com.edo979.postpractice.idling.ComposeCountingIdlingResource
import com.edo979.postpractice.idling.attachIdling
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

class IdlingRemotePostRepository(
    private val remotePostRepository: PostRepository,
    private val countingIdlingResource: ComposeCountingIdlingResource
) : PostRepository {
    lateinit var post: Post

    override fun getPosts() = remotePostRepository.getPosts().attachIdling(countingIdlingResource)
    override fun getPost(id: Long): Flow<Post> {
        Log.d("CountingIdlingResource", "GET remote post $id")
        runBlocking {
            remotePostRepository.getPost(id).attachIdling(countingIdlingResource).collect {
                post = it
            }
        }
        return flowOf(post)
    }
}