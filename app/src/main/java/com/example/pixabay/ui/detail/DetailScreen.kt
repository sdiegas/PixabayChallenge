package com.example.pixabay.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.pixabay.ui.theme.PixabayTheme

@Composable
fun DetailScreen(
    imageUrl: String,
    name: String,
    tags: String,
    likes: Int,
    downloads: Int,
    comments: Int
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
        )
        Text("Name: $name")
        Text("Tags: $tags")
        Text("Likes: $likes")
        Text("Downloads: $downloads")
        Text("Comments: $comments")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PixabayTheme {
        Surface {
            DetailScreen("https://pixabay.com/get/g8713a8135f323bffa8913fa373a2f79c130c9dc92b791ee0ecf91af1a1bd09d8ad8789f727c5b93f57e402a80d2110dd7874302a04210a506a74a71a48683a54_1280.jpg", "Stefan", "Football, Soccer", 510, 10, 15)
        }
    }
}