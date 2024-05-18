package com.ltu.m7019e.moviedb.v24.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ltu.m7019e.moviedb.v24.model.Movie
import com.ltu.m7019e.moviedb.v24.utils.Constants
import com.ltu.m7019e.moviedb.v24.viewmodel.MovieDBViewModel
import com.ltu.m7019e.moviedb.v24.viewmodel.SelectedMovieUiState

@Composable
fun MovieDetailScreen(
    selectedMovieUiState: SelectedMovieUiState,
    modifier: Modifier = Modifier,
    onReviewButtonClicked: () -> Unit,
    movieDBViewModel: MovieDBViewModel,
) {
    val selectedMovieUiState = movieDBViewModel.selectedMovieUiState
    when (selectedMovieUiState) {
        is SelectedMovieUiState.Success -> {
            Column(Modifier.fillMaxWidth()) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp)
                        .background(Color.Black)
                ) {
                    AsyncImage(
                        model = Constants.BACKDROP_IMAGE_BASE_URL + Constants.BACKDROP_IMAGE_WIDTH + selectedMovieUiState.movie.backdropPath,
                        contentDescription = selectedMovieUiState.movie.title,
                        modifier = modifier.align(Alignment.Center),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = selectedMovieUiState.movie.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = selectedMovieUiState.movie.releaseDate,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = selectedMovieUiState.movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.size(8.dp))
                Button(
                    onClick = onReviewButtonClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text("Reviews")
                }
                Row {
                    Text(
                        text = "Favorite",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Switch(checked = selectedMovieUiState.isFavorite, onCheckedChange = {
                        if (it)
                            movieDBViewModel.saveFavouriteMovie(selectedMovieUiState.movie)
                        else
                            movieDBViewModel.deleteSavedFavouriteMovie(selectedMovieUiState.movie)
                    })
                }
            }
        }
        is SelectedMovieUiState.Loading -> {
            Text(
                text = "Loading...",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
        is SelectedMovieUiState.Error -> {
            Text(
                text = "Error...",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

//@Composable
//fun MovieShowGenres(
//    movie: Movie
//) {
//    Text(text = movie.genres.map{it.name}.joinToString(", " ))
//}

@Composable
fun MovieShowHTMLLink(
    modifier: Modifier = Modifier,
    onHTMLLinkButtonClicked: () -> Unit
) {
    Column {
        Button(
            onClick = onHTMLLinkButtonClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Homepage")
        }
    }
}

@Composable
fun MovieOpenToOtherApp(
    modifier: Modifier = Modifier,
    onOpenToOtherAppButtonClicked: () -> Unit
) {
    Column {
        Button(
            onClick = onOpenToOtherAppButtonClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Open in IMDB App")
        }
    }
}