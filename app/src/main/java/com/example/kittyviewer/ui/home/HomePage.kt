package com.example.kittyviewer.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kittyviewer.R
import com.example.kittyviewer.data.model.Kitty
import com.example.kittyviewer.ui.designsystem.KittyCard
import com.example.kittyviewer.ui.util.isLandscape

@Composable
fun Home(kitties: List<Kitty>, scrollState: LazyGridState, modifier: Modifier = Modifier) {
    var kitty by remember { mutableStateOf(Kitty("", "", -1, -1)) }
    Box(modifier = Modifier.fillMaxSize()) {
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
                    kitty = it
                }
            }
        }
        if (kitty.width > 0) {
            val context = LocalContext.current
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(kitty.url)
                    .error(R.drawable.sad_face)
                    .fallback(R.drawable.sad_face)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.cat),
                contentDescription = "thumbnail",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 20.dp, horizontal = 10.dp)
                    .background(shape = RoundedCornerShape(10.dp), color = Color.Transparent)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
                    .clickable { kitty = Kitty("", "", -1, -1) },
                contentScale = ContentScale.FillHeight
            )
        }
    }
}

private const val CELL_COUNT_PORTRAIT = 3
private const val CELL_COUNT_LANDSCAPE = 6

@Preview
@Composable
fun HomePreview() {
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