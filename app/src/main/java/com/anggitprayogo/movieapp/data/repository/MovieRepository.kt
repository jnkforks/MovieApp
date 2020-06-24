package com.anggitprayogo.movieapp.data.repository

import androidx.paging.PagingData
import com.anggitprayogo.movieapp.data.local.entity.FavouriteEntity
import com.anggitprayogo.movieapp.data.local.entity.MovieEntity
import com.anggitprayogo.movieapp.data.remote.entity.Movie
import com.anggitprayogo.movieapp.data.remote.entity.MovieDetail
import com.anggitprayogo.movieapp.data.remote.entity.MovieReviews
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
interface MovieRepository {

    /**
     * Remote
     */
    fun getPopularMoviePagingSource(): Flow<PagingData<MovieEntity>>

    fun getUpcomingMoviePagingSource(): Flow<PagingData<MovieEntity>>

    fun getNowPlayingMoviePagingSource(): Flow<PagingData<MovieEntity>>

    fun getTopRatedMoviePagingSource(): Flow<PagingData<MovieEntity>>

    suspend fun getDetailMovie(movieId: String): Response<MovieDetail>

    suspend fun getMovieReviewsByMovieId(movieId: String): Response<MovieReviews>


    /**
     * Local
     */
    suspend fun fetchAllMoviesDao(): List<FavouriteEntity>

    suspend fun fetchMovieByMovieId(movieId: Int): List<FavouriteEntity>

    suspend fun insertMovie(favouriteEntity: FavouriteEntity)

    suspend fun deleteMovie(favouriteEntity: FavouriteEntity)
}