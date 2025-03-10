package com.edo979.presentation_post.single

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.edo979.domain.usecase.GetPostUseCase
import com.edo979.domain.usecase.favoritePost.DeleteFavoritePostUseCase
import com.edo979.domain.usecase.favoritePost.SaveFavoritePostUseCase
import com.edo979.presentation_common.state.MviViewModel
import com.edo979.presentation_common.state.UiSingleEvent
import com.edo979.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPosUseCase: GetPostUseCase,
    private val savePostUseCase: SaveFavoritePostUseCase,
    private val deletePostUseCase: DeleteFavoritePostUseCase,
    private val converter: PostConverter
) : MviViewModel<PostModel, UiState<PostModel>, PostUiAction, UiSingleEvent>() {

    override fun initState(): UiState<PostModel> = UiState.Loading

    override fun uiStateFlowOnStart() {}

    override fun handleAction(action: PostUiAction) {
        when (action) {
            is PostUiAction.Load -> loadPost(action.postId)
            is PostUiAction.SaveToFavorites -> savePost(action.post)
            is PostUiAction.DeleteFromFavorites -> deletePost(action.post)
        }
    }

    private fun loadPost(postId: Long) {
        viewModelScope.launch {
            getPosUseCase.execute(GetPostUseCase.Request(postId = postId)).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
    }

    private fun savePost(post: PostModel) {
        viewModelScope.launch {
            savePostUseCase.execute(SaveFavoritePostUseCase.Request(post.toDomain()))
                .catch {
                    Log.d("favoritePost", "error: $it")
                }
                .collect()
        }
    }

    private fun deletePost(model: PostModel) {
        viewModelScope.launch {
            deletePostUseCase.execute(DeleteFavoritePostUseCase.Request(model.toDomain()))
                .catch {
                    Log.d("favoritePost", "error: $it")
                }
                .collect()
        }
    }
}