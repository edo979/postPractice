package com.edo979.presentation_post.list

import com.edo979.presentation_common.state.UiAction

sealed class PostListUiAction : UiAction {
    data object Load : PostListUiAction()
    data class PostClick(val postId: Long) : PostListUiAction()
    data class TabIndexChanged(val index: Int) : PostListUiAction()
    data class SearchQueryChanged(val query: String) : PostListUiAction()
}