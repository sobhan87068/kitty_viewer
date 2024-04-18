package com.example.kittyviewer.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kittyviewer.data.model.Kitty
import com.example.kittyviewer.ui.designsystem.KittyCard
import com.example.kittyviewer.ui.util.isLandscape

@Composable
fun Home(kitties: List<Kitty>, scrollState: LazyGridState, modifier: Modifier = Modifier) {
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
            KittyCard(kitty = it)
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
            Kitty("1", "title", 0, 0),
            Kitty("2", "title", 0, 0),
            Kitty("3", "title", 0, 0),
            Kitty("4", "title", 0, 0),
            Kitty("5", "title", 0, 0),
        ), scrollState = rememberLazyGridState()
    )
}