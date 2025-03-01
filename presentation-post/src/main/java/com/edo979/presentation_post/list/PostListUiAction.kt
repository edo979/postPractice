package com.edo979.presentation_post.list

import com.edo979.presentation_common.state.UiAction

sealed class PostListUiAction : UiAction {
    data object Load : PostListUiAction()
    data class TabClick(val index: Int) : PostListUiAction()
}