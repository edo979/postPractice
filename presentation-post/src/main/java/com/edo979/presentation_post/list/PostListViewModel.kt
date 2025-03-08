package com.edo979.presentation_post.list

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.edo979.domain.usecase.GetPostsWithUsersUseCase
import com.edo979.presentation_common.navigation.NavRoutes
import com.edo979.presentation_common.navigation.PostRouteArg
import com.edo979.presentation_common.state.MviViewModel
import com.edo979.presentation_common.state.UiState
import com.edo979.presentation_common.state.UiState.*
import com.edo979.presentation_post.list.PostListUiSingleEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAB_INDEX_KEY = "tabIndex"

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val getPosts: GetPostsWithUsersUseCase,
    private val converter: PostListConverter,
    private val savedStateHandle: SavedStateHandle
) : MviViewModel<PostListModel, UiState<PostListModel>, PostListUiAction, PostListUiSingleEvent>() {

    var tabIndex: Int
        get() = savedStateHandle[TAB_INDEX_KEY] ?: 0
        private set(value) {
            savedStateHandle[TAB_INDEX_KEY] = value
        }

    override fun initState(): UiState<PostListModel> = UiState.Loading
    override fun uiStateFlowOnStart() {
        submitAction(PostListUiAction.Load)
        Log.d("tabs", tabIndex.toString())
    }

    override fun handleAction(action: PostListUiAction) {
        when (action) {
            is PostListUiAction.Load -> loadPosts()
            is PostListUiAction.PostClick -> submitSingleEvent(
                OpenPostScreen(
                    NavRoutes.Post.routeForPost(PostRouteArg(action.postId))
                )
            )

            is PostListUiAction.TabIndexChanged -> {
                tabIndex = action.index
                submitState(
                    Success(
                        (uiStateFlow.value as UiState.Success).data.copy(
                            tabIndex = 1
                        )
                    )
                )
            }

            is PostListUiAction.SearchQueryChanged -> TODO()
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            getPosts.execute(GetPostsWithUsersUseCase.Request).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
    }
}