package com.anggitprayogo.movieapp.domain

import com.anggitprayogo.core.util.ext.safeApiCall
import com.anggitprayogo.core.util.state.ResultState
import com.anggitprayogo.movieapp.data.enum.MovieFilter
import com.anggitprayogo.movieapp.data.local.entity.MovieEntity
import com.anggitprayogo.movieapp.data.remote.entity.MovieDetail
import com.anggitprayogo.movieapp.data.remote.entity.MovieReviews
import com.anggitprayogo.movieapp.data.remote.entity.Movies
import com.anggitprayogo.movieapp.data.repository.MovieRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    /**
     * Remote
     */
    suspend fun getMoviesByType(movieFilter: MovieFilter): ResultState<Movies> {
        return safeApiCall {
            val response = fetchMovieByType(movieFilter)
            try {
                ResultState.Success(response.body()!!)
            } catch (e: Exception) {
                ResultState.Error(e.localizedMessage, response.code())
            }
        }
    }

    private suspend fun fetchMovieByType(movieFilter: MovieFilter): Response<Movies> {
        return when (movieFilter) {
            MovieFilter.POPULAR -> movieRepository.getPopularMovie()
            MovieFilter.NOW_PLAYING -> movieRepository.getNowPlayingMovie()
            MovieFilter.UP_COMING -> movieRepository.getUpcomingMovie()
            MovieFilter.TOP_RATED -> movieRepository.getTopRated()
        }
    }

    suspend fun getMovieDetail(movieId: String): ResultState<MovieDetail> {
        return safeApiCall {
            val response = movieRepository.getDetailMovie(movieId)
            try {
                ResultState.Success(response.body()!!)
            } catch (e: Exception) {
                ResultState.Error(e.localizedMessage, response.code())
            }
        }
    }

    suspend fun getMovieReviewsByMovieId(movieId: String): ResultState<MovieReviews> {
        return safeApiCall {
            val response = movieRepository.getMovieReviewsByMovieId(movieId)
            try {
                ResultState.Success(response.body()!!)
            } catch (e: Exception) {
                ResultState.Error(e.localizedMessage, response.code())
            }
        }
    }


    /**
     * Local Db Movie Dao
     */
    suspend fun getFavouriteMovie(): ResultState<List<MovieEntity>> {
        return try {
            val response = movieRepository.fetchAllMoviesDao()
            ResultState.Success(response)
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage, 500)
        }
    }

    suspend fun getMovieDetailByMovieId(movieId: Int): ResultState<List<MovieEntity>> {
        return try {
            val response = movieRepository.fetchMovieByMovieId(movieId)
            ResultState.Success(response)
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage, 500)
        }
    }

    suspend fun insertMovieToDb(movieEntity: MovieEntity) {
        try {
            movieRepository.insertMovie(movieEntity)
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    suspend fun deleteMovieFromDb(movieEntity: MovieEntity) {
        try {
            movieRepository.deleteMovie(movieEntity)
        } catch (e: Exception) {
            throw Exception(e)
        }
    }
}