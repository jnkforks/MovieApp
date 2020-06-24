package com.anggitprayogo.movieapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anggitprayogo.movieapp.data.local.entity.MovieEntity

/**
 * Created by Anggit Prayogo on 6/23/20.
 */
@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    fun fetchAll(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()
}