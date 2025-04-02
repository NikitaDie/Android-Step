package com.nikitadev.moviefavorites.view

import androidx.compose.foundation.layout.Row
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nikitadev.moviefavorites.viewmodel.MovieViewModel

@Composable
fun MovieLandscapeScreen(
    viewModel: MovieViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    Box(modifier = modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            uiState.error != null -> {
                Text(
                    text = uiState.error!!,
                    color = Color.Red,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    state = listState
                ) {
                    items(uiState.movies) { movie ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Left side: Movie Details
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 16.dp)
                                ) {
                                    Text(
                                        text = movie.title,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    Text(
                                        text = "Release Date: ${movie.releaseDate ?: "Unknown"}",
                                        fontSize = 14.sp,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    Text(
                                        text = movie.overview,
                                        fontSize = 14.sp,
                                        color = Color.White,
                                        maxLines = 4,
                                        //overflow = androidx.compose.ui.text.TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    Text(
                                        text = "Popularity: ${movie.popularity}",
                                        fontSize = 12.sp,
                                        color = Color.LightGray,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    Text(
                                        text = "Rating: ⭐ ${movie.formattedRating}/10",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFFFD700),
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                }

                                // Right side: Movie Poster
                                AsyncImage(
                                    model = movie.posterUrl,
                                    contentDescription = movie.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(width = 120.dp, height = 180.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .align(Alignment.CenterVertically)
                                )
                            }
                        }
                    }

                    if (uiState.isLoadingMore) {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                LaunchedEffect(listState) {
                    snapshotFlow { listState.layoutInfo }
                        .collect { layoutInfo ->
                            val totalItems = layoutInfo.totalItemsCount
                            val lastVisibleItem =
                                layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                            if (totalItems > 0 && lastVisibleItem >= totalItems - 5) {
                                viewModel.loadMoreMovies()
                            }
                        }
                }
            }
        }
    }
}