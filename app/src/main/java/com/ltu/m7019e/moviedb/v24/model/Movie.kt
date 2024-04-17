package com.ltu.m7019e.moviedb.v24.model
data class Movie(
    var id: Long = 0L,
    var title: String,
    var posterPath: String,
    var backdropPath: String,
    var releaseDate: String,
    var genres: ArrayList<Genre>, //https://api.themoviedb.org/3/genre/movie/list?api_key={api_key}&language=en-US
    var overview: String
)
