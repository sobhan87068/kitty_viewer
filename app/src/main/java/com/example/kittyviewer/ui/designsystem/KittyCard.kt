package com.example.kittyviewer.ui.designsystem

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kittyviewer.R
import com.example.kittyviewer.data.model.Kitty

@Composable
fun KittyCard(kitty: Kitty) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth(.3f)
            .fillMaxHeight()
            .padding(horizontal = 8.dp, vertical = 20.dp)
            .clickable {
                Toast
                    .makeText(context, "${kitty.width}x${kitty.height}", Toast.LENGTH_LONG)
                    .show()
            },
        horizontalAlignment = Alignment.CenterHorizontally
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
                .fillMaxWidth()
                .aspectRatio(9f / 16f)
                .padding(bottom = 20.dp)
                .background(shape = RoundedCornerShape(10.dp), color = Color.Transparent)
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp)),
            contentScale = ContentScale.FillHeight
        )
        Text(
            text = "${kitty.width}x${kitty.height}",
            fontWeight = FontWeight(600),
            fontSize = 12.sp, lineHeight = 14.sp,
            maxLines = 1, overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun KittyCardPreview() {
    KittyCard(
        kitty = Kitty(
            id = "test",
            url = "https://cdn2.thecatapi.com/images/a49.jpg",
            width = -1,
            height = -1
        )
    )
}