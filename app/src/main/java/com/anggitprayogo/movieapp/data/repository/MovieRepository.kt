package com.anggitprayogo.movieapp.data.repository

import com.anggitprayogo.movieapp.data.local.entity.MovieEntity
import com.anggitprayogo.movieapp.data.remote.entity.MovieDetail
import com.anggitprayogo.movieapp.data.remote.entity.MovieReviews
import com.anggitprayogo.movieapp.data.remote.entity.Movies
import retrofit2.Response

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
interface MovieRepository {

    /**
     * Remote
     */
    suspend fun getPopularMovie(): Response<Movies>

    suspend fun getUpcomingMovie(): Response<Movies>

    suspend fun getNowPlayingMovie(): Response<Movies>

    suspend fun getTopRated(): Response<Movies>

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