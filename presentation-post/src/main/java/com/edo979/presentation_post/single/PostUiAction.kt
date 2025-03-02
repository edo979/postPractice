package com.edo979.presentation_post.single

import com.edo979.presentation_common.state.UiAction

sealed class PostUiAction : UiAction {

    data class Load(val postId: Long) : PostUiAction()
}