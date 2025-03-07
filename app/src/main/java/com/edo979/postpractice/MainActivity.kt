package com.edo979.postpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edo979.postpractice.ui.theme.PostPracticeTheme
import com.edo979.presentation_common.navigation.NavRoutes
import com.edo979.presentation_post.list.PostListScreenRoot
import com.edo979.presentation_post.single.PostScreenRoot
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PostPracticeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        val navController = rememberNavController()
                        APP(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun APP(navController: NavHostController) {
    NavHost(navController, startDestination = NavRoutes.Posts.route) {

        composable(route = NavRoutes.Posts.route) {
            PostListScreenRoot(
                viewModel = hiltViewModel(),
                navController = navController,
            )
        }

        composable(route = NavRoutes.Post.route, arguments = NavRoutes.Post.arguments) {
            val id = NavRoutes.Post.fromEntry(it).id
            PostScreenRoot(viewModel = hiltViewModel(), postId = id)
        }
    }
}

