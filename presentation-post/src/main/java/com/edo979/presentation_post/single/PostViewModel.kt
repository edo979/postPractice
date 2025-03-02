package com.edo979.presentation_post.single

import androidx.lifecycle.viewModelScope
import com.edo979.domain.usecase.GetPostWithUserWithIsFavoriteUseCase
import com.edo979.presentation_common.state.MviViewModel
import com.edo979.presentation_common.state.UiSingleEvent
import com.edo979.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val useCase: GetPostWithUserWithIsFavoriteUseCase, private val converter: PostConverter
) : MviViewModel<PostModel, UiState<PostModel>, PostUiAction, UiSingleEvent>() {

    override fun initState(): UiState<PostModel> = UiState.Loading

    override fun uiStateFlowOnStart() {}

    override fun handleAction(action: PostUiAction) {
        when (action) {
            is PostUiAction.Load -> loadPost(action.postId)
        }
    }

    private fun loadPost(postId: Long) {
        viewModelScope.launch {
            useCase.execute(GetPostWithUserWithIsFavoriteUseCase.Request(postId = postId)).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
    }
}