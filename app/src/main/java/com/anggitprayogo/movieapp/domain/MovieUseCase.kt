package com.anggitprayogo.movieapp.domain

import com.anggitprayogo.core.util.ext.safeApiCall
import com.anggitprayogo.core.util.state.ResultState
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
}