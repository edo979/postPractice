package com.edo979.postpractice

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.edo979.postpractice.ui.theme.PostPracticeTheme
import com.edo979.presentation_post.list.PostListItemModel
import com.edo979.presentation_post.list.PostListModel
import com.edo979.presentation_post.list.PostListScreen

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
        PostListScreen(postListState.items)
    }
}