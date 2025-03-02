package com.edo979.postpractice

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.edo979.postpractice.ui.theme.PostPracticeTheme
import com.edo979.presentation_common.navigation.NavRoutes
import com.edo979.presentation_post.list.PostListItemModel
import com.edo979.presentation_post.list.PostListModel
import com.edo979.presentation_post.list.PostListScreen
import com.edo979.presentation_post.single.SinglePostScreen

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
        PostListScreen(postListState.items, onAction = {})
    }
}

@Preview
@Composable
private fun SinglePostScreenPreview() {
    PostPracticeTheme {
        SinglePostScreen(2L)
    }
}