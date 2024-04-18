package com.example.kittyviewer.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kittyviewer.R
import com.example.kittyviewer.data.model.Kitty

@Composable
fun DetailsPage(kitty: Kitty, viewModel: DetailsViewModel) {
    val context = LocalContext.current
    val isBookmarked by viewModel.isBookmarked.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = kitty) {
        viewModel.submitAction(DetailsAction.GetBookmarkState(kitty))
    }

    Box(
        modifier = Modifier
            .background(color = Color(0x22ffffff))
            .padding(vertical = 60.dp, horizontal = 10.dp)
    ) {
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
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp)),
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
            painter = painterResource(
                id = if (isBookmarked) R.drawable.heart else R.drawable.heart_outline
            ),
            contentDescription = "bookmark",
            modifier = Modifier
                .padding(bottom = 30.dp)
                .size(50.dp)
                .background(color = Color.White, shape = CircleShape)
                .padding(all = 5.dp)
                .align(Alignment.BottomCenter)
                .zIndex(1f)
                .clickable {
                    viewModel.submitAction(
                        if (isBookmarked) DetailsAction.RemoveBookmark(kitty)
                        else DetailsAction.AddBookmark(kitty)
                    )
                }
        )
    }
}