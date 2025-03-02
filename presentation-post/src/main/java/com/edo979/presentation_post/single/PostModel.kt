package com.edo979.presentation_post.single

data class PostModel(
    val id: Long,
    val author: String,
    val authorEmail: String,
    val title: String,
    val body: String
)