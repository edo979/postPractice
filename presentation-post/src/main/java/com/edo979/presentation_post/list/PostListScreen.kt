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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.edo979.presentation_common.state.CommonScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PostListScreenRoot(
    viewModel: PostListViewModel, navController: NavHostController
) {

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest { singleUiEventAction ->
            when (singleUiEventAction) {
                is PostListUiSingleEvent.OpenPostScreen -> navController.navigate(
                    singleUiEventAction.navRoute
                )
            }
        }
    }

    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state) { postListModel ->
            PostListScreen(
                posts = postListModel.items,
                searchQuery = postListModel.searchQuery,
                favoritePosts = postListModel.favoriteItems,
                savedTabIndex = viewModel.tabIndex,
                onAction = { viewModel.submitAction(it) }
            )
        }
    }
}

@Composable
fun PostListScreen(
    posts: List<PostListItemModel>,
    searchQuery: String,
    savedTabIndex: Int,
    favoritePosts: List<PostListItemModel>,
    onAction: (PostListUiAction) -> Unit
) {
    var tabIndex by remember { mutableIntStateOf(savedTabIndex) }
    val pagerState = rememberPagerState(tabIndex) { 2 }

    LaunchedEffect(tabIndex) {
        pagerState.animateScrollToPage(tabIndex)
        onAction(PostListUiAction.TabIndexChanged(tabIndex))
    }

    LaunchedEffect(pagerState.currentPage) {
        tabIndex = pagerState.currentPage
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(top = 32.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(searchQuery = searchQuery, onAction = onAction)

        Surface(
            modifier = Modifier
                .padding(top = 32.dp)
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth()
                ) {

                    Tab(
                        selected = pagerState.currentPage == 0,
                        onClick = { tabIndex = 0 },
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 12.dp),
                    ) {
                        Text(
                            text = "New Posts",
                            fontWeight = if (pagerState.currentPage == 0) FontWeight.ExtraBold else FontWeight.Normal
                        )
                    }

                    Tab(
                        selected = pagerState.currentPage == 1,
                        onClick = { tabIndex = 1 },
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = "Favorite Posts",
                            fontWeight = if (pagerState.currentPage == 1) FontWeight.ExtraBold else FontWeight.Normal
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                HorizontalPager(
                    state = pagerState, modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { pageIndex ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        when (pageIndex) {
                            0 -> {
                                PostList(posts = posts, onAction = onAction)
                            }

                            1 -> {
                                PostList(
                                    posts = favoritePosts, onAction = onAction
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}