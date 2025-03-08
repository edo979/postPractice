package com.edo979.presentation_post.list

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.edo979.domain.usecase.GetPostsWithUsersUseCase
import com.edo979.presentation_common.navigation.NavRoutes
import com.edo979.presentation_common.navigation.PostRouteArg
import com.edo979.presentation_common.state.MviViewModel
import com.edo979.presentation_common.state.UiState
import com.edo979.presentation_post.list.PostListUiSingleEvent.OpenPostScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
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

    lateinit var cachedSuccessState: UiState.Success<PostListModel>

    override fun initState(): UiState<PostListModel> = UiState.Loading
    override fun uiStateFlowOnStart() {
        submitAction(PostListUiAction.Load)
        observeSearchQuery()
    }

    override fun handleAction(action: PostListUiAction) {
        when (action) {
            is PostListUiAction.Load -> loadPosts()
            is PostListUiAction.PostClick -> submitSingleEvent(
                OpenPostScreen(
                    NavRoutes.Post.routeForPost(PostRouteArg(action.postId))
                )
            )

            is PostListUiAction.TabIndexChanged -> tabIndex = action.index
            is PostListUiAction.SearchQueryChanged -> searchPosts(action.query)
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            getPosts.execute(GetPostsWithUsersUseCase.Request).map {
                converter.convert(it)
            }.collect {
                if (it is UiState.Success) cachedSuccessState = it
                submitState(it)
            }
        }
    }

    private fun searchPosts(query: String) {
        Log.d("searchQ", query)

        // TODO: check if is the state success
        submitState(
            UiState.Success(
                data = (uiStateFlow.value as UiState.Success).data.copy(
                    searchQuery = query
                )
            )
        )

    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        if (uiStateFlow.value is UiState.Success) {
            uiStateFlow.map {
                (it as UiState.Success).data.searchQuery
            }.distinctUntilChanged().debounce(500L).onEach { query ->
                when {
                    query.isBlank() -> {
                        submitState(cachedSuccessState)
                    }

                    query.length >= 2 -> {
                        searchPosts(query)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}