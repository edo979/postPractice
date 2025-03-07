package com.edo979.presentation_post.single

import com.edo979.domain.entity.Post
import com.edo979.domain.entity.PostWithUser
import com.edo979.domain.entity.User

data class PostModel(
    val id: Long,
    val userId: Long,
    val author: String,
    val userName: String,
    val authorEmail: String,
    val title: String,
    val body: String,
    val isFavorite: Boolean
)

fun PostModel.toDomain(): PostWithUser = PostWithUser(
    post = Post(
        id = this.id, userId = this.userId, title = this.title, body = this.body
    ), user = User(
        id = this.userId, name = this.author, username = this.userName, email = this.authorEmail
    )
)