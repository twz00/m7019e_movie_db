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
    Review(title = R.string.movie_Review),
    Detail(title = R.string.movie_detail)
}


/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
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
fun MovieDBApp(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
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
        val context = LocalContext.current
        val movieDBViewModel: MovieDBViewModel = viewModel(factory = MovieDBViewModel.Factory)

        NavHost(
            navController = navController,
            startDestination = MovieDBScreen.List.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = MovieDBScreen.List.name) {
                MovieListScreen(
                    movieListUiState = movieDBViewModel.movieListUiState,
                    onMovieListItemClicked = {
                        movieDBViewModel.setSelectedMovie(it)
                        navController.navigate(MovieDBScreen.Detail.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
            composable(route = MovieDBScreen.Detail.name) {
                Column {
                    MovieDetailScreen(
                        selectedMovieUiState = movieDBViewModel.selectedMovieUiState,
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
            composable(route = MovieDBScreen.Review.name) {
                MovieReviewScreen(
                    selectedMovieUiState = movieDBViewModel.selectedMovieUiState,
                    modifier = Modifier,
                )
            }
        }
    }
}