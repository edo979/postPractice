package com.edo979.postpractice

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.edo979.postpractice.ui.theme.PostPracticeTheme
import com.edo979.presentation_post.list.PostListItemModel
import com.edo979.presentation_post.list.PostListModel
import com.edo979.presentation_post.list.PostListScreen
import com.edo979.presentation_post.single.PostModel
import com.edo979.presentation_post.single.PostScreen

private val postListState = PostListModel(
    items = (1..3).map {
        PostListItemModel(
            id = it.toLong(),
            userId = 1L,
            author = "author$it",
            title = "title$it"
        )
    }
)

@Preview
@Composable
private fun PostListScreenPreview() {
    PostPracticeTheme {
        PostListScreen(
            posts = postListState.items,
            savedTabIndex = 0,
            favoritePosts = postListState.items,
            onNavigateToDetails = {},
            onTabIndexChanged = {})
    }
}

private val post = PostModel(
    id = 1L,
    author = "author",
    authorEmail = "author@email.em",
    title = "Some Title for post",
    body = "Body of a post",
    userName = "Username",
    userId = 1L,
    isFavorite = true
)

@Preview
@Composable
private fun SinglePostScreenPreview() {
    PostPracticeTheme {
        PostScreen(post, toggleFavoritePost = {})
    }
}