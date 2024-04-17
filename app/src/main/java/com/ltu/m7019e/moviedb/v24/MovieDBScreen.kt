package com.ltu.m7019e.moviedb.v24

import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ltu.m7019e.moviedb.v24.database.Movies
import com.ltu.m7019e.moviedb.v24.model.Movie
import com.ltu.m7019e.moviedb.v24.ui.screens.MovieDetailScreen
import com.ltu.m7019e.moviedb.v24.ui.screens.MovieListScreen
import com.ltu.m7019e.moviedb.v24.ui.screens.MovieOpenToOtherApp
import com.ltu.m7019e.moviedb.v24.ui.screens.MovieReviewScreen
import com.ltu.m7019e.moviedb.v24.ui.screens.MovieShowHTMLLink
import com.ltu.m7019e.moviedb.v24.viewmodel.MovieDBViewModel

enum class MovieDBScreen(@StringRes val title: Int) {
    List(title = R.string.app_name),
    Detail(title = R.string.movie_Detail),
    Review(title = R.string.movie_Review),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDBAppBar(
    currentScreen: MovieDBScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun TheMovieDBApp(
    viewModel: MovieDBViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = MovieDBScreen.valueOf(
        backStackEntry?.destination?.route ?: MovieDBScreen.List.name
    )

    Scaffold(
        topBar = {
            MovieDBAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current

        NavHost(
            navController = navController,
            startDestination = MovieDBScreen.List.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = MovieDBScreen.List.name) {
                MovieListScreen(
                    movieList = Movies().getMovies(),
                    onMovieListItemClicked = { movie ->
                        viewModel.setSelectedMovie(movie)
                        navController.navigate(MovieDBScreen.Detail.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = MovieDBScreen.Detail.name) {
                uiState.selectedMovie?.let { movie ->
                    Column {
                        MovieDetailScreen(
                            movie = movie,
                            modifier = Modifier,
                            onReviewButtonClicked = {
                                navController.navigate(MovieDBScreen.Review.name)
                            },
                        )
                        MovieShowHTMLLink(
                            onHTMLLinkButtonClicked = {
                                val urlIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://www.themoviedb.org/movie/587996-bajocero")
                                )
                                context.startActivity(urlIntent)
                            }
                        )
                        MovieOpenToOtherApp(
                            onOpenToOtherAppButtonClicked = {
                                val urlIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://www.imdb.com/title/tt0108160/")
                                )
                                context.startActivity(urlIntent)
                            }
                        )
                    }
                }
            }
            composable(route = MovieDBScreen.Review.name) {
                uiState.selectedMovie?.let { movie: Movie ->
                    MovieReviewScreen(
                        movie = movie,
                        modifier = Modifier,
                    )
                }
            }
        }

    }
}
