package com.anggitprayogo.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.anggitprayogo.movieapp.data.local.entity.MovieEntity

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    suspend fun fetchAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE uid=:uid")
    suspend fun fetchMovieById(uid: Long): MovieEntity

    @Query("SELECT * FROM movies WHERE movie_id=:movieId")
    suspend fun fetchMovieByMovieId(movieId: Int): List<MovieEntity>

    @Insert
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Delete
    suspend fun deleteMovie(movieEntity: MovieEntity)
}