package com.edo979.presentation_post.list

import com.edo979.presentation_common.state.MviViewModel
import com.edo979.presentation_common.state.UiState

class PostListViewModel :
    MviViewModel<PostListModel, UiState<PostListModel>, PostListUiAction, PostListUiSingleEvent>() {

    override fun initState(): UiState<PostListModel> {
        TODO("Not yet implemented")
    }

    override fun handleAction(action: PostListUiAction) {
        TODO("Not yet implemented")
    }
}