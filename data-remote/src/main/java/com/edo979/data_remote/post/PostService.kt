package com.edo979.data_remote.post

import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {

    @GET("/posts")
    suspend fun getPosts(): List<PostApiModel>

    @GET("/posts/{postId}")
    suspend fun getPost(@Path("postId") id: Long): PostApiModel
}