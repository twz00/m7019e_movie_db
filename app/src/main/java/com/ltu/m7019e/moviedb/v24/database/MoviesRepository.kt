package com.ltu.m7019e.moviedb.v24.database

import com.ltu.m7019e.moviedb.v24.model.Movie
import com.ltu.m7019e.moviedb.v24.model.MovieResponse
import com.ltu.m7019e.moviedb.v24.model.ReviewResponse
import com.ltu.m7019e.moviedb.v24.model.VideoResponse
import com.ltu.m7019e.moviedb.v24.network.MovieDBApiService

interface MoviesRepository {
    suspend fun getPopularMovies(): MovieResponse
    suspend fun getTopRatedMovies(): MovieResponse
    suspend fun getSelectedMovieReviews(id: Long): ReviewResponse
    suspend fun getSelectedMovieVideos(id: Long): VideoResponse
}

interface CachedMovieRepository {
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getMovie(id: Long): Movie?
    suspend fun updateCache(type: CachedMovieType,movies: List<Movie>)

    suspend fun getSavedFavouriteMovies(): List<Movie>

    suspend fun saveFavouriteMovie(movie: Movie)

    suspend fun deleteSavedFavouriteMovie(movie: Movie)
}

class NetworkMoviesRepository(private val apiService: MovieDBApiService) : MoviesRepository {
    override suspend fun getPopularMovies(): MovieResponse {
        return apiService.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): MovieResponse {
        return apiService.getTopRatedMovies()
    }

    override suspend fun getSelectedMovieReviews(id: Long): ReviewResponse {
        return apiService.getSelectedMovieReviews(id = id)
    }

    override suspend fun getSelectedMovieVideos(id: Long): VideoResponse {
        return apiService.getSelectedMovieVideos(id = id)
    }
}

class CachedMoviesRepository(private val movieDao: MovieDao) : CachedMovieRepository {
    override suspend fun getPopularMovies(): List<Movie> {
        return if (currentCachedMovieType == CachedMovieType.POPULAR) {
            movieDao.getCachedMovies()
        } else {
            return emptyList()
        }
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        return if (currentCachedMovieType == CachedMovieType.TOP_RATED) {
            movieDao.getCachedMovies()
        } else {
            return emptyList()
        }
    }

    override suspend fun getMovie(id: Long): Movie? {
        return movieDao.getMovie(id)
    }

    override suspend fun updateCache(type: CachedMovieType, movies: List<Movie>) {
        currentCachedMovieType = type
        movieDao.deleteAllNotFavouriteCachedMovies()
        movieDao.insertCachedMovies(movies)
    }

    override suspend fun getSavedFavouriteMovies(): List<Movie> {
        return movieDao.getFavoriteMovies()
    }

    override suspend fun saveFavouriteMovie(movie: Movie) {
        movieDao.insertFavoriteMovie(movie.id)
    }

    override suspend fun deleteSavedFavouriteMovie(movie: Movie) {
        movieDao.deleteFavoriteMovie(movie.id)
    }

    companion object {
        private var currentCachedMovieType: CachedMovieType = CachedMovieType.NONE
    }
}

enum class CachedMovieType {
    NONE, POPULAR, TOP_RATED
}
