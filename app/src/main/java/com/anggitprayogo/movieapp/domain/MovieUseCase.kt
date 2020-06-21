package com.anggitprayogo.movieapp.domain

import com.anggitprayogo.core.util.ext.safeApiCall
import com.anggitprayogo.core.util.state.ResultState
import com.anggitprayogo.movieapp.data.db.entity.MovieEntity
import com.anggitprayogo.movieapp.data.entity.MovieDetail
import com.anggitprayogo.movieapp.data.entity.Movies
import com.anggitprayogo.movieapp.data.repository.MovieRepository
import javax.inject.Inject

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend fun getPopularMovie(): ResultState<Movies> {
        return safeApiCall {
            val response = movieRepository.getPopularMovie()
            try {
                ResultState.Success(response.body()!!)
            } catch (e: Exception) {
                ResultState.Error("", response.code())
            }
        }
    }

    suspend fun getUpcomingMovie(): ResultState<Movies> {
        return safeApiCall {
            val response = movieRepository.getUpcomingMovie()
            try {
                ResultState.Success(response.body()!!)
            } catch (e: Exception) {
                ResultState.Error("", response.code())
            }
        }
    }

    suspend fun getNowPlayingMovie(): ResultState<Movies> {
        return safeApiCall {
            val response = movieRepository.getNowPlayingMovie()
            try {
                ResultState.Success(response.body()!!)
            } catch (e: Exception) {
                ResultState.Error("", response.code())
            }
        }
    }

    suspend fun getTopRatedMovie(): ResultState<Movies> {
        return safeApiCall {
            val response = movieRepository.getTopRated()
            try {
                ResultState.Success(response.body()!!)
            } catch (e: Exception) {
                ResultState.Error("", response.code())
            }
        }
    }

    suspend fun getMovieDetail(movieId: String): ResultState<MovieDetail> {
        return safeApiCall {
            val response = movieRepository.getDetailMovie(movieId)
            try {
                ResultState.Success(response.body()!!)
            } catch (e: Exception) {
                ResultState.Error("", response.code())
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

    suspend fun getMovieDetailByMovieId(movieId: Int): ResultState<MovieEntity> {
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
}