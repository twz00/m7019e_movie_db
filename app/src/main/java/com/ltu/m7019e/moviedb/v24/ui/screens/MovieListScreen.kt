package com.ltu.m7019e.moviedb.v24.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ltu.m7019e.moviedb.v24.model.Genre
import com.ltu.m7019e.moviedb.v24.model.Movie
import com.ltu.m7019e.moviedb.v24.network.ConnectionState
import com.ltu.m7019e.moviedb.v24.network.connectivityState
import com.ltu.m7019e.moviedb.v24.ui.theme.TheMovideDBV24Theme
import com.ltu.m7019e.moviedb.v24.utils.Constants
import com.ltu.m7019e.moviedb.v24.viewmodel.MovieListUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun HomeScreen(movieListUiState: MovieListUiState,
               onMovieListItemClicked: (Movie) -> Unit,
) {
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    if (isConnected) {
        BoxWithConstraints {
            if (maxWidth > 400.dp) {
                MovieGridScreen(
                    movieListUiState = movieListUiState,
                    onMovieListItemClicked = onMovieListItemClicked,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                MovieListScreen(
                    movieListUiState = movieListUiState,
                    onMovieListItemClicked = onMovieListItemClicked,
                    modifier = Modifier.padding(16.dp)
                )
                //            MovieGridScreen(
                //                movieListUiState = movieListUiState,
                //                onMovieListItemClicked = onMovieListItemClicked,
                //                modifier = Modifier.padding(16.dp),
                //            )
            }
        }
    } else {
        Text(text = "No Connection Test")
    }
}
@Composable
fun MovieListScreen(movieListUiState: MovieListUiState,
                    onMovieListItemClicked: (Movie) -> Unit,
                    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {

        when(movieListUiState) {
            is MovieListUiState.Success -> {
                items(movieListUiState.movies) { movie ->
                    MovieListItemCard(
                        movie = movie,
                        onMovieListItemClicked,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            is MovieListUiState.Loading -> {
                item {
                    Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            is MovieListUiState.Error -> {
                item {
                    Text(
                        text = "Error: Something went wrong!",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListItemCard(movie: Movie,
                      onMovieListItemClicked: (Movie) -> Unit,
                      modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = {
            onMovieListItemClicked(movie)
        }
    ) {
        Row {
            Box {
                AsyncImage(
                    model = Constants.POSTER_IMAGE_BASE_URL + Constants.POSTER_IMAGE_WIDTH + movie.posterPath,
                    contentDescription = movie.title,
                    modifier = modifier
                        .width(92.dp)
                        .height(138.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Column {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = movie.releaseDate,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@Composable
fun MovieGridScreen(movieListUiState: MovieListUiState,
                    onMovieListItemClicked: (Movie) -> Unit,
                    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = modifier) {
        when(movieListUiState) {
            is MovieListUiState.Success -> {
                items(movieListUiState.movies) { movie ->
                    MovieListItemCard(
                        movie = movie,
                        onMovieListItemClicked,
                        modifier = Modifier.padding(8.dp),
                    )
                }
            }

            is MovieListUiState.Loading -> {
                item {
                    Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            is MovieListUiState.Error -> {
                item {
                    Text(
                        text = "Error: Something went wrong!",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    TheMovideDBV24Theme {
//        MovieListItemCard(
//            movie = Movie(
//                1,
//                "Raya and the Last Dragon",
//                "/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg",
//                "/9xeEGUZjgiKlI69jwIOi0hjKUIk.jpg",
//                "2021-03-03",
//                arrayListOf(
//                    Genre(16, "Animation"),
//                    Genre(10751, "Family"),
//                    Genre(14, "Fantasy"),
//                    Genre(28, "Action"),
//                    Genre(12, "Adventure")
//                ),
//                "Long ago, in the fantasy world of Kumandra, humans and dragons lived together in harmony. But when an evil force threatened the land, the dragons sacrificed themselves to save humanity. Now, 500 years later, that same evil has returned and it’s up to a lone warrior, Raya, to track down the legendary last dragon to restore the fractured land and its divided people."
//            ), {}
//        )
//    }
//}