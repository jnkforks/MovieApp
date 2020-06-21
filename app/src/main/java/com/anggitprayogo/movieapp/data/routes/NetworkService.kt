package com.anggitprayogo.movieapp.data.routes

import com.anggitprayogo.movieapp.data.entity.Movies
import retrofit2.Response
import retrofit2.http.GET

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
}