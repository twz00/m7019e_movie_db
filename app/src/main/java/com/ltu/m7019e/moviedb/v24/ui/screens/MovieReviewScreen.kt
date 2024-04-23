package com.ltu.m7019e.moviedb.v24.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.media3.common.MediaItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.ltu.m7019e.moviedb.v24.model.Movie
import com.ltu.m7019e.moviedb.v24.model.Video
import com.ltu.m7019e.moviedb.v24.utils.Constants
import com.ltu.m7019e.moviedb.v24.viewmodel.SelectedMovieReviewsUiState
import com.ltu.m7019e.moviedb.v24.viewmodel.SelectedMovieVideosUiState
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieReviewScreen(
    selectedMovie: Movie?,
    selectedMovieReviewsUiState: SelectedMovieReviewsUiState,
    selectedMovieVideosUiState: SelectedMovieVideosUiState,
    onYoutubeVideoListItemClicked: (Video) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        when (selectedMovieReviewsUiState) {
            is SelectedMovieReviewsUiState.Success -> {
                Text(
                    text = "Reviews for " + selectedMovie?.title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.size(8.dp))
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight(0.5f),

                    ) {
                    items(selectedMovieReviewsUiState.reviews) { review ->
                        Card(
                            modifier = Modifier
                                .padding(16.dp)
                                .width(250.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = review.author,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                                Text(
                                    text = review.content,
                                    style = MaterialTheme.typography.bodySmall,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }
                    }
                }
            }

            is SelectedMovieReviewsUiState.Loading -> {
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(16.dp)
                )
            }

            is SelectedMovieReviewsUiState.Error -> {
                Text(
                    text = "Error...",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        when (selectedMovieVideosUiState) {
            is SelectedMovieVideosUiState.Success -> {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxHeight(0.5f),

                    ) {
                    items(selectedMovieVideosUiState.videos) { video ->
                        var videoPlayerVisible = remember {
                            mutableStateOf(false)
                        }
                        Card(
                            onClick = {
                                if (video.site == "YouTube") {
                                    onYoutubeVideoListItemClicked(video)
                                } else {
                                    videoPlayerVisible.value = true
                                }
                            },
                            modifier = Modifier
                                .padding(16.dp)
                                .width(250.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.Black),
                        ) {
                            if (videoPlayerVisible.value)
                                VideoPlayer(url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
                            else {
                                Column (modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center) {
                                    Text(
                                        text = "Play Video",
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                        modifier = Modifier
                                            .background(Color.Black)
                                    )
                                }

                            }
                        }
                    }
                }
            }

            is SelectedMovieVideosUiState.Loading -> {
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(16.dp)
                )
            }

            is SelectedMovieVideosUiState.Error -> {
                Text(
                    text = "Error...",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun VideoPlayer(url: String) {
    // Initialize ExoPlayer
    val exoPlayer = ExoPlayer.Builder(LocalContext.current).build()

    // Create a MediaSource
    val mediaSource = remember {
        MediaItem.fromUri(url)
    }

    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
    }

    // Manage lifecycle events
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // Set MediaSource to ExoPlayer
    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
    }

    // Manage lifecycle events
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // Use AndroidView to embed an Android View (PlayerView) into Compose
    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
            }
        },
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(200.dp) // Set your desired height
    )
}

