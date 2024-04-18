package com.example.kittyviewer.ui.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kittyviewer.ui.designsystem.KittyCard
import com.example.kittyviewer.ui.util.LocalNavController
import com.example.kittyviewer.ui.util.isLandscape

@Composable
fun Bookmarks(bookmarkViewModel: BookmarkViewModel) {
    val scrollState = rememberLazyGridState()
    val navController = LocalNavController.current
    val bookmarksState by bookmarkViewModel.bookmarks.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = null) {
        bookmarkViewModel.submitAction(BookmarkAction.GetBookmarks)
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Bookmarks",
                fontWeight = FontWeight(600),
                fontSize = 18.sp, lineHeight = 20.sp,
                maxLines = 1, overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .weight(1f)
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(
                    if (isLandscape())
                        CELL_COUNT_LANDSCAPE
                    else CELL_COUNT_PORTRAIT
                ),
                contentPadding = PaddingValues(horizontal = 5.dp),
                userScrollEnabled = true,
                state = scrollState,
            ) {
                items(items = bookmarksState, key = { item ->
                    item.id
                }) {
                    KittyCard(kitty = it) {
                        navController.currentBackStackEntry?.savedStateHandle?.set("kitty", it)
                        navController.navigate("details")
                    }
                }
            }
        }
    }
}

private const val CELL_COUNT_PORTRAIT = 3
private const val CELL_COUNT_LANDSCAPE = 6