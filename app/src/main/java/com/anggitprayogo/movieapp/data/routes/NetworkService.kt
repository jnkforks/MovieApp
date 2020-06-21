package com.anggitprayogo.movieapp.data.routes

import com.anggitprayogo.movieapp.data.entity.MovieDetail
import com.anggitprayogo.movieapp.data.entity.MovieReviews
import com.anggitprayogo.movieapp.data.entity.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
interface NetworkService {

    @GET("movie/popular")
    suspend fun getPopularMovie(): Response<Movies>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovie(): Response<Movies>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovie(): Response<Movies>

    @GET("movie/top_rated")
    suspend fun getTopRated(): Response<Movies>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(@Path("movie_id") movieId: String): Response<MovieDetail>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviewsByMovieId(@Path("movie_id") movieId: String): Response<MovieReviews>
}