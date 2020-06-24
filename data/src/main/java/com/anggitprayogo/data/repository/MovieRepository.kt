package com.anggitprayogo.data.repository

import androidx.paging.PagingData
import com.anggitprayogo.data.local.entity.MovieEntity
import com.anggitprayogo.data.remote.entity.Movie
import com.anggitprayogo.data.remote.entity.MovieDetail
import com.anggitprayogo.data.remote.entity.MovieReviews
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
interface MovieRepository {

    /**
     * Remote
     */
    fun getPopularMoviePagingSource(): Flow<PagingData<Movie>>

    fun getUpcomingMoviePagingSource(): Flow<PagingData<Movie>>

    fun getNowPlayingMoviePagingSource(): Flow<PagingData<Movie>>

    fun getTopRatedMoviePagingSource(): Flow<PagingData<Movie>>

    suspend fun getDetailMovie(movieId: String): Response<MovieDetail>

    suspend fun getMovieReviewsByMovieId(movieId: String): Response<MovieReviews>


    /**
     * Local
     */
    suspend fun fetchAllMoviesDao(): List<MovieEntity>

    suspend fun fetchMovieByMovieId(movieId: Int): List<MovieEntity>

    suspend fun insertMovie(movieEntity: MovieEntity)

    suspend fun deleteMovie(movieEntity: MovieEntity)
}