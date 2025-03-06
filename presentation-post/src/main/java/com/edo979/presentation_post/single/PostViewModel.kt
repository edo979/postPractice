package com.edo979.presentation_post.single

import androidx.lifecycle.viewModelScope
import com.edo979.domain.usecase.GetPostUseCase
import com.edo979.domain.usecase.favoritePost.SaveFavoritePostUseCase
import com.edo979.presentation_common.state.MviViewModel
import com.edo979.presentation_common.state.UiSingleEvent
import com.edo979.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val useCase: GetPostUseCase,
    private val savePostUseCase: SaveFavoritePostUseCase,
    private val converter: PostConverter
) : MviViewModel<PostModel, UiState<PostModel>, PostUiAction, UiSingleEvent>() {

    override fun initState(): UiState<PostModel> = UiState.Loading

    override fun uiStateFlowOnStart() {}

    override fun handleAction(action: PostUiAction) {
        when (action) {
            is PostUiAction.Load -> loadPost(action.postId)
            is PostUiAction.SaveToFavorites -> savePost(action.post)
        }
    }

    private fun loadPost(postId: Long) {
        viewModelScope.launch {
            useCase.execute(GetPostUseCase.Request(postId = postId)).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
    }

    private fun savePost(post: PostModel) {
        viewModelScope.launch {
            savePostUseCase.execute(SaveFavoritePostUseCase.Request(post.toDomain()))
        }
    }
}