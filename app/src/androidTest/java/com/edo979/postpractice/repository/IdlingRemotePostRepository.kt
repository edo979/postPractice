package com.edo979.postpractice.repository

import com.edo979.domain.repository.PostRepository
import com.edo979.postpractice.idling.ComposeCountingIdlingResource
import com.edo979.postpractice.idling.attachIdling

class IdlingRemotePostRepository(
    private val remotePostRepository: PostRepository,
    private val countingIdlingResource: ComposeCountingIdlingResource
) : PostRepository {

    override fun getPosts() = remotePostRepository.getPosts().attachIdling(countingIdlingResource)
    override fun getPost(id: Long) =
        remotePostRepository.getPost(id).attachIdling(countingIdlingResource)

}