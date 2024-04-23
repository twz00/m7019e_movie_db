package com.ltu.m7019e.moviedb.v24.network

import com.ltu.m7019e.moviedb.v24.model.MovieResponse
import com.ltu.m7019e.moviedb.v24.model.ReviewResponse
import com.ltu.m7019e.moviedb.v24.model.VideoResponse
import com.ltu.m7019e.moviedb.v24.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBApiService {

    @GET("popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieResponse

    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ): MovieResponse

    @GET("{id}/reviews")
    suspend fun getSelectedMovieReviews(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ) : ReviewResponse

    @GET("{id}/videos")
    suspend fun getSelectedMovieVideos(
        @Path("id") id: Long,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ) : VideoResponse
}