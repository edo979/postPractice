package com.edo979.domain.entity

data class PostWithData(
    val post: Post,
    val user: User,
    val isFavorite: Boolean
)
