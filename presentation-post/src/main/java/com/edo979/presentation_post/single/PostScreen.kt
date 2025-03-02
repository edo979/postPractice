package com.edo979.presentation_post.single

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.edo979.presentation_common.state.CommonScreen

@Composable
fun PostScreenRoot(viewModel: PostViewModel, postId: Long) {
    LaunchedEffect(postId) {
        viewModel.submitAction(PostUiAction.Load(postId))
    }

    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state) { post ->
            PostScreen(post)
        }
    }
}

@Composable
fun PostScreen(post: PostModel) {
    Column {
        Text("Single ${post.title}")
        Text("Single ${post.author}")
        Text("Single ${post.authorEmail}")
        Text("Single ${post.body}")
    }
}