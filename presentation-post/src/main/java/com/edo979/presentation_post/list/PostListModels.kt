package com.edo979.presentation_post.list

data class PostListItemModel(
    val id: Long,
    val userId: Long,
    val author: String,
    val authorUserName: String,
    val title: String,
    val body: String
)

data class PostListModel(
    val items: List<PostListItemModel> = listOf(),
    val favoriteItems: List<PostListItemModel> = listOf(),
    val searchQuery: String = "",
)