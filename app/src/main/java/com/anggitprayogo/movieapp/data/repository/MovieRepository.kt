package com.anggitprayogo.movieapp.data.repository

import com.anggitprayogo.movieapp.data.db.entity.MovieEntity
import com.anggitprayogo.movieapp.data.entity.MovieDetail
import com.anggitprayogo.movieapp.data.entity.Movies
import retrofit2.Response

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
interface MovieRepository {

    suspend fun getPopularMovie(): Response<Movies>

    suspend fun getUpcomingMovie(): Response<Movies>

    suspend fun getNowPlayingMovie(): Response<Movies>

    suspend fun getTopRated(): Response<Movies>

    suspend fun getDetailMovie(movieId: String): Response<MovieDetail>

    suspend fun fetchAllMoviesDao(): List<MovieEntity>

    suspend fun fetchMovieByMovieId(movieId: Int): List<MovieEntity>

    suspend fun insertMovie(movieEntity: MovieEntity)

    suspend fun deleteMovie(movieEntity: MovieEntity)
}