package com.edo979.presentation_post.single

import com.edo979.presentation_common.state.UiAction

sealed class PostUiAction : UiAction {

    data class Load(val postId: Long) : PostUiAction()
    data class SaveToFavorites(val post: PostModel) : PostUiAction()
    data class DeleteFromFavorites(val post: PostModel) : PostUiAction()
}