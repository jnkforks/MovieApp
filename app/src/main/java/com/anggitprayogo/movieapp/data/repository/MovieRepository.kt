package com.anggitprayogo.movieapp.data.repository

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
}