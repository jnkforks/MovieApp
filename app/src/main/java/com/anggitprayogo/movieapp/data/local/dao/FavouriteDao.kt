package com.anggitprayogo.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.anggitprayogo.movieapp.data.local.entity.FavouriteEntity

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourites")
    suspend fun fetchAllMovies(): List<FavouriteEntity>

    @Query("SELECT * FROM favourites WHERE uid=:uid")
    suspend fun fetchMovieById(uid: Long): FavouriteEntity

    @Query("SELECT * FROM favourites WHERE movie_id=:movieId")
    suspend fun fetchMovieByMovieId(movieId: Int): List<FavouriteEntity>

    @Insert
    suspend fun insertMovie(favouriteEntity: FavouriteEntity)

    @Delete
    suspend fun deleteMovie(favouriteEntity: FavouriteEntity)
}