package com.edo979.presentation_post.single

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp)
            .verticalScroll(scrollState)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            PostDataRow("author:", post.author)
            PostDataRow("email:", post.authorEmail)
            Text(
                text = "Single ${post.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End),
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = 32.dp)
                    .weight(1f),
                thickness = 1.dp,
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.weight(0.05f))
            IconButton(modifier = Modifier.wrapContentWidth(Alignment.End), onClick = {}) {
                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null)
            }
        }

        Text(
            text = "Single ${post.body}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun PostDataRow(data: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = value, style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = data, style = MaterialTheme.typography.titleMedium)
    }
}