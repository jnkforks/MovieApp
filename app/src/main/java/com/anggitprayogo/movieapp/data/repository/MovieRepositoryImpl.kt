package com.anggitprayogo.movieapp.data.repository

import com.anggitprayogo.movieapp.data.entity.MovieDetail
import com.anggitprayogo.movieapp.data.entity.Movies
import com.anggitprayogo.movieapp.data.routes.NetworkService
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: NetworkService
) : MovieRepository {

    override suspend fun getPopularMovie(): Response<Movies> {
        return service.getPopularMovie()
    }

    override suspend fun getUpcomingMovie(): Response<Movies> {
        return service.getUpcomingMovie()
    }

    override suspend fun getNowPlayingMovie(): Response<Movies> {
        return service.getNowPlayingMovie()
    }

    override suspend fun getTopRated(): Response<Movies> {
        return service.getTopRated()
    }

    override suspend fun getDetailMovie(movieId: String): Response<MovieDetail> {
        return service.getDetailMovie(movieId)
    }
}