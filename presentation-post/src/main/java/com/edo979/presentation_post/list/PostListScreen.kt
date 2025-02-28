package com.edo979.presentation_post.list

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.edo979.presentation_common.state.CommonScreen

@Composable
fun PostListScreen(
    modifier: Modifier = Modifier,
    viewModel: PostListViewModel
) {
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state) {
            Text(it.items[1].title)
        }
    }
}