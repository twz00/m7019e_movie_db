package com.ltu.m7019e.moviedb.v24.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ltu.m7019e.moviedb.v24.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM cached_movies WHERE favourite")
    suspend fun getFavoriteMovies(): List<Movie>

    @Query("UPDATE cached_movies SET favourite = 1 WHERE id = :id")
    suspend fun insertFavoriteMovie(id: Long)

    @Query("SELECT * FROM cached_movies WHERE id = :id")
    suspend fun getMovie(id: Long): Movie

    @Query("UPDATE cached_movies SET favourite = 0 WHERE id = :id")
    suspend fun deleteFavoriteMovie(id: Long)

    @Query("SELECT * FROM cached_movies")
    suspend fun getCachedMovies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCachedMovies(movies: List<Movie>)

    @Query("DELETE FROM cached_movies WHERE NOT favourite")
    suspend fun deleteAllNotFavouriteCachedMovies()

}