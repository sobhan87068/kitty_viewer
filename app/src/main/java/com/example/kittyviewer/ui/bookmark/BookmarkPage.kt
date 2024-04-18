package com.example.kittyviewer.ui.bookmark

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kittyviewer.R
import com.example.kittyviewer.data.model.Kitty
import com.example.kittyviewer.ui.designsystem.KittyCard
import com.example.kittyviewer.ui.util.isLandscape

@Composable
fun Bookmarks(bookmarkViewModel: BookmarkViewModel) {
    val scrollState = rememberLazyGridState()
    val context = LocalContext.current
    var kitty by remember { mutableStateOf(Kitty("", "", -1, -1)) }
    val bookmarksState by bookmarkViewModel.bookmarks.collectAsStateWithLifecycle()

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
                        kitty = it
                    }
                }
            }
            if (kitty.width > 0) {
                Box(modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp)) {
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
                            .background(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.Transparent
                            )
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
                            .clickable { kitty = Kitty("", "", -1, -1) },
                        contentScale = ContentScale.FillHeight
                    )
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .zIndex(1f)
                            .align(Alignment.TopStart)
                    ) {

                        Text(
                            text = kitty.id,
                            fontWeight = FontWeight(600),
                            fontSize = 18.sp, lineHeight = 20.sp,
                            maxLines = 1, overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${kitty.width}x${kitty.height}",
                            fontWeight = FontWeight(400),
                            fontSize = 16.sp, lineHeight = 18.sp,
                            maxLines = 1, overflow = TextOverflow.Ellipsis
                        )
                    }

                    Image(
                        painter = painterResource(id = R.drawable.heart_outline),
                        contentDescription = "bookmark",
                        modifier = Modifier
                            .padding(bottom = 30.dp)
                            .size(50.dp)
                            .background(color = Color.White, shape = CircleShape)
                            .padding(all = 5.dp)
                            .align(Alignment.BottomCenter)
                            .zIndex(1f)
                    )
                }
            }
        }
    }
}

private const val CELL_COUNT_PORTRAIT = 3
private const val CELL_COUNT_LANDSCAPE = 6