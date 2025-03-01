package com.edo979.presentation_post.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
            Text(it.items[1].title)
        }
    }
}

@Composable
fun PostListScreen(postListModel: PostListModel) {
    val pagerState = rememberPagerState(0) { 2 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Search Bar")

        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
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
                        modifier = Modifier.weight(1f),
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

                Spacer(modifier = Modifier.height(4.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { page ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        when (page) {
                            0 -> {
                                Text("First tab content")
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