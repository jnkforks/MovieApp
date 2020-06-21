package com.anggitprayogo.movieapp.data.repository

import com.anggitprayogo.movieapp.data.db.dao.MovieDao
import com.anggitprayogo.movieapp.data.db.entity.MovieEntity
import com.anggitprayogo.movieapp.data.entity.MovieDetail
import com.anggitprayogo.movieapp.data.entity.MovieReviews
import com.anggitprayogo.movieapp.data.entity.Movies
import com.anggitprayogo.movieapp.data.routes.NetworkService
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: NetworkService,
    private val movieDao: MovieDao
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

    override suspend fun getMovieReviewsByMovieId(movieId: String): Response<MovieReviews> {
        return service.getMovieReviewsByMovieId(movieId)
    }

    override suspend fun fetchAllMoviesDao(): List<MovieEntity> {
        return movieDao.fetchAllMovies()
    }

    override suspend fun fetchMovieByMovieId(movieId: Int): List<MovieEntity> {
        return movieDao.fetchMovieByMovieId(movieId)
    }

    override suspend fun insertMovie(movieEntity: MovieEntity) {
        return movieDao.insertMovie(movieEntity)
    }

    override suspend fun deleteMovie(movieEntity: MovieEntity) {
        return movieDao.deleteMovie(movieEntity)
    }
}