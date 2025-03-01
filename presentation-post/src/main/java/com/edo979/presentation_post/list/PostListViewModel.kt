package com.edo979.presentation_post.list

import androidx.lifecycle.viewModelScope
import com.edo979.domain.usecase.GetPostsWithUsersUseCase
import com.edo979.presentation_common.state.MviViewModel
import com.edo979.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val useCase: GetPostsWithUsersUseCase,
    private val converter: PostListConverter
) :
    MviViewModel<PostListModel, UiState<PostListModel>, PostListUiAction, PostListUiSingleEvent>() {

    override fun initState(): UiState<PostListModel> = UiState.Loading
    override fun uiStateFlowOnStart() {
        submitAction(PostListUiAction.Load)
    }

    override fun handleAction(action: PostListUiAction) {
        when (action) {
            is PostListUiAction.Load -> loadPost()
            is PostListUiAction.TabClick -> {}
        }
    }

    private fun loadPost() {
        viewModelScope.launch {
            useCase.execute(GetPostsWithUsersUseCase.Request).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
    }
}