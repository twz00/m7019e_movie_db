package com.ltu.m7019e.moviedb.v24.database

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