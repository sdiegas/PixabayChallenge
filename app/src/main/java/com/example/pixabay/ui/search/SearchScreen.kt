package com.example.pixabay.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pixabay.data.remote.models.PixabayImage
import com.example.pixabay.data.remote.models.PixabayResult
import com.example.pixabay.ui.theme.PixabayTheme

@Composable
fun PixabaySearchResults(navController: NavController?, searchViewModel: SearchViewModel = hiltViewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            val searchQuery = searchViewModel.searchQueryState.collectAsState()
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                value = searchQuery.value,
                onValueChange = { searchViewModel.updateSearchQuery(it)},
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                shape = RoundedCornerShape(corner = CornerSize(16.dp))
            )
            when (val state = searchViewModel.uiState.collectAsState().value) {
                is SearchViewModel.SearchUiState.Empty -> EmptyScreen()
                is SearchViewModel.SearchUiState.Loading -> LoadingScreen()
                is SearchViewModel.SearchUiState.Error -> ErrorScreen(message = state.message)
                is SearchViewModel.SearchUiState.Loaded -> PixabayImagesLoaded(navController, state.data)
            }
        }
    }
}

@Composable
fun PixabayImagesLoaded(navController: NavController?, data: PixabayResult) {
    LazyColumn {
        items(
            items = data.hits,
            itemContent = {
                PixabayListItem(navController, it)
            }
        )
    }
}

@Composable
fun PixabayListItem(navController: NavController?, data: PixabayImage) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { navController?.navigate("detail/${data.user}/${data.tags}/${data.likes}/${data.downloads}/${data.comments}?imageUrl=${data.largeImageURL}") },
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(data.previewURL),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(64.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
            )
            Column {
                Text(text = data.user, style = MaterialTheme.typography.h6)
                Text(text = data.tags, style = MaterialTheme.typography.caption)
            }
        }
    }
}

@Composable
fun EmptyScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No Results found")
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(modifier = Modifier.size(64.dp))
    }
}

@Composable
fun ErrorScreen(message: String) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Error")
            },
            text = {
                Text(message)
            },
            confirmButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PixabayTheme {
        PixabaySearchResults(null)
    }
}
