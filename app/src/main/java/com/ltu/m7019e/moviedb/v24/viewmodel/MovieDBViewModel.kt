package com.ltu.m7019e.moviedb.v24.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ltu.m7019e.moviedb.v24.MovieDBApplication
import com.ltu.m7019e.moviedb.v24.database.FavoriteMoviesRepository
import com.ltu.m7019e.moviedb.v24.database.MoviesRepository
import com.ltu.m7019e.moviedb.v24.database.SavedMovieRepository
import com.ltu.m7019e.moviedb.v24.model.Movie
import com.ltu.m7019e.moviedb.v24.model.Review
import com.ltu.m7019e.moviedb.v24.model.Video
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface MovieListUiState {
    data class Success(val movies: List<Movie>) : MovieListUiState
    object Error : MovieListUiState
    object Loading : MovieListUiState
}

sealed interface SelectedMovieUiState {
    data class Success(val movie: Movie, val isFavorite: Boolean) : SelectedMovieUiState
    object Error : SelectedMovieUiState
    object Loading : SelectedMovieUiState
}

sealed interface SelectedMovieReviewsUiState {
    data class Success(val reviews: List<Review>): SelectedMovieReviewsUiState
    object Error : SelectedMovieReviewsUiState
    object Loading : SelectedMovieReviewsUiState
}

sealed interface SelectedMovieVideosUiState {
    data class Success(val videos: List<Video>): SelectedMovieVideosUiState
    object Error : SelectedMovieVideosUiState
    object Loading : SelectedMovieVideosUiState
}

class MovieDBViewModel(private val moviesRepository: MoviesRepository, private val savedMovieRepository: SavedMovieRepository) : ViewModel() {

    var movieListUiState: MovieListUiState by mutableStateOf(MovieListUiState.Loading)
        private set

    var selectedMovieUiState: SelectedMovieUiState by mutableStateOf(SelectedMovieUiState.Loading)
        private set

    var selectedMovieReviewsUiState: SelectedMovieReviewsUiState
    by mutableStateOf(SelectedMovieReviewsUiState.Loading)
        private set

    var selectedMovieVideosUiState: SelectedMovieVideosUiState
            by mutableStateOf(SelectedMovieVideosUiState.Loading)
        private set

    var movieSelected by mutableStateOf<Movie?>(null)

    init {
        getPopularMovies()
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            movieListUiState = try {
                MovieListUiState.Success(moviesRepository.getTopRatedMovies().results)
            } catch (e: IOException) {
                MovieListUiState.Error
            } catch (e: HttpException) {
                MovieListUiState.Error
            }
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            movieListUiState = try {
                MovieListUiState.Success(moviesRepository.getPopularMovies().results)
            } catch (e: IOException) {
                MovieListUiState.Error
            } catch (e: HttpException) {
                MovieListUiState.Error
            }
        }
    }

    fun getSavedMovies() {
        viewModelScope.launch {
            movieListUiState = MovieListUiState.Loading
            movieListUiState = try {
                MovieListUiState.Success(savedMovieRepository.getSavedMovies())
            } catch (e: IOException) {
                MovieListUiState.Error
            } catch (e: HttpException) {
                MovieListUiState.Error
            }
        }
    }

    fun saveMovie(movie: Movie) {
        viewModelScope.launch {
            savedMovieRepository.insertMovie(movie)
            selectedMovieUiState = SelectedMovieUiState.Success(movie, true)
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            savedMovieRepository.deleteMovie(movie)
            selectedMovieUiState = SelectedMovieUiState.Success(movie, false)
        }
    }

    fun setSelectedMovie(movie: Movie) {
        viewModelScope.launch {
            selectedMovieUiState = SelectedMovieUiState.Loading
            selectedMovieUiState = try {
                movieSelected = movie
                SelectedMovieUiState.Success(movie, savedMovieRepository.getMovie(movie.id) != null)
            } catch (e: IOException) {
                SelectedMovieUiState.Error
            } catch (e: HttpException) {
                SelectedMovieUiState.Error
            }
        }
    }

    fun getSelectedMovieReviews(){
        viewModelScope.launch {
            selectedMovieReviewsUiState = SelectedMovieReviewsUiState.Loading
            selectedMovieReviewsUiState = try {
                if (movieSelected != null) {
                    SelectedMovieReviewsUiState.Success(
                        moviesRepository.getSelectedMovieReviews(movieSelected!!.id).results
                    )
                }
                else {
                    throw IOException("Selected Movie is null!")
                }
            } catch (e: IOException) {
                SelectedMovieReviewsUiState.Error
            } catch (e: HttpException) {
                SelectedMovieReviewsUiState.Error
            }
        }
    }

    fun getSelectedMovieVideos(){
        viewModelScope.launch {
            selectedMovieVideosUiState = SelectedMovieVideosUiState.Loading
            selectedMovieVideosUiState = try {
                if (movieSelected != null) {
                    SelectedMovieVideosUiState.Success(
                        moviesRepository.getSelectedMovieVideos(movieSelected!!.id).results
                    )
                }
                else {
                    throw IOException("Selected Movie is null!")
                }
            } catch (e: IOException) {
                SelectedMovieVideosUiState.Error
            } catch (e: HttpException) {
                SelectedMovieVideosUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieDBApplication)
                val moviesRepository = application.container.moviesRepository
                val savedMovieRepository = application.container.savedMovieRepository
                MovieDBViewModel(moviesRepository = moviesRepository, savedMovieRepository = savedMovieRepository)
            }
        }
    }
}