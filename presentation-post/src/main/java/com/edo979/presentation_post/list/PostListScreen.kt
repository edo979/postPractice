package com.edo979.presentation_post.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.edo979.presentation_common.state.CommonScreen

@Composable
fun PostListScreenRoot(viewModel: PostListViewModel) {
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state) {
            PostListScreen(posts = it.items)
        }
    }
}

@Composable
fun PostListScreen(posts: List<PostListItemModel>) {
    val pagerState = rememberPagerState(0) { 2 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar()

        Surface(
            modifier = Modifier
                .padding(top = 32.dp)
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TabRow(
                    selectedTabIndex = 0,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth()
                ) {

                    Tab(
                        selected = true,
                        onClick = {},
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 12.dp),
                    ) {
                        Text("First Tab")
                    }

                    Tab(
                        selected = false,
                        onClick = {},
                        modifier = Modifier.weight(1f),
                    ) {
                        Text("Second Tab")
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { page ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        when (page) {
                            0 -> {
                                PostList(posts = posts)
                            }

                            1 -> {
                                Text("Second tab content")
                            }
                        }
                    }
                }
            }
        }
    }
}