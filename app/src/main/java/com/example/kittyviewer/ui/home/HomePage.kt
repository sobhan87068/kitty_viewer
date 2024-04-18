package com.example.kittyviewer.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.kittyviewer.R
import com.example.kittyviewer.data.model.Kitty
import com.example.kittyviewer.ui.designsystem.KittyCard
import com.example.kittyviewer.ui.util.LocalNavController
import com.example.kittyviewer.ui.util.isLandscape

@Composable
fun Home(kitties: List<Kitty>, scrollState: LazyGridState, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val navController = LocalNavController.current
    val onBookmarkClick = remember {
        {
            navController.navigate("bookmarks")
        }
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = context.getString(R.string.app_name),
                fontWeight = FontWeight(600),
                fontSize = 18.sp, lineHeight = 20.sp,
                maxLines = 1, overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(start = 70.dp)
                    .weight(1f)
            )

            Image(
                painter = painterResource(id = R.drawable.bookmark_multiple),
                contentDescription = "bookmarks",
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(40.dp)
                    .background(color = Color.White, shape = CircleShape)
                    .padding(all = 5.dp)
                    .clickable(onClick = onBookmarkClick),
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
                state = scrollState, modifier = modifier
            ) {
                items(items = kitties, key = { item ->
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

@Preview
@Composable
fun HomePreview() {
    CompositionLocalProvider(LocalNavController provides rememberNavController()) {
        Home(
            kitties = listOf(
                Kitty("1", "https://cdn2.thecatapi.com/images/a49.jpg", 90, 160),
                Kitty("2", "https://cdn2.thecatapi.com/images/a49.jpg", 90, 160),
                Kitty("3", "https://cdn2.thecatapi.com/images/a49.jpg", 90, 160),
                Kitty("4", "https://cdn2.thecatapi.com/images/a49.jpg", 90, 160),
                Kitty("5", "https://cdn2.thecatapi.com/images/a49.jpg", 90, 160),
            ), scrollState = rememberLazyGridState()
        )
    }
}