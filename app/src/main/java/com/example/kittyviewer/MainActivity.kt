package com.example.kittyviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kittyviewer.ui.bookmark.BookmarkViewModel
import com.example.kittyviewer.ui.bookmark.Bookmarks
import com.example.kittyviewer.ui.designsystem.LoadMoreError
import com.example.kittyviewer.ui.home.ApiState
import com.example.kittyviewer.ui.home.Home
import com.example.kittyviewer.ui.home.HomeAction
import com.example.kittyviewer.ui.home.HomeViewModel
import com.example.kittyviewer.ui.theme.KittyListTheme
import com.example.kittyviewer.ui.util.LocalNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel.submitAction(HomeAction.LoadMore)
        setContent {
            val navController = rememberNavController()
            CompositionLocalProvider(LocalNavController provides navController) {
                KittyListTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(navController = navController, startDestination = "/home") {
                            composable("/home") {
                                MainPage(homeViewModel) {
                                    homeViewModel.submitAction(HomeAction.LoadMore)
                                }
                            }

                            composable("/bookmarks") {
                                Bookmarks(bookmarkViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainPage(viewModel: HomeViewModel, loadMore: () -> Unit) {
    val scrollState = rememberLazyGridState()
    val endReached by remember {
        derivedStateOf {
            !scrollState.canScrollForward
        }
    }
    val state by viewModel.apiState.collectAsStateWithLifecycle()
    val kittiesState by viewModel.kittiesState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = endReached) {
        if (endReached)
            loadMore()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
    ) {
        Home(kitties = kittiesState.kitties, scrollState = scrollState)

        if (state is ApiState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 20.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        } else if (state is ApiState.Error) {
            LoadMoreError(message = (state as ApiState.Error).message, loadMore)
        }
    }
}