package com.anggitprayogo.movieapp.data.remote.routes

import com.anggitprayogo.movieapp.data.remote.entity.MovieDetail
import com.anggitprayogo.movieapp.data.remote.entity.MovieReviews
import com.anggitprayogo.movieapp.data.remote.entity.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
interface NetworkService {

    @GET("movie/popular")
    suspend fun getPopularMovie(@Query("page") page: String): Response<Movies>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovie(@Query("page") page: String): Response<Movies>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovie(@Query("page") page: String): Response<Movies>

    @GET("movie/top_rated")
    suspend fun getTopRated(@Query("page") page: String): Response<Movies>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(@Path("movie_id") movieId: String): Response<MovieDetail>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviewsByMovieId(@Path("movie_id") movieId: String): Response<MovieReviews>
}