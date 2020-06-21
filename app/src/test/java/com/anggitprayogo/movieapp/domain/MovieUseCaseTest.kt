package com.anggitprayogo.movieapp.domain

import com.anggitprayogo.core.util.state.ResultState
import com.anggitprayogo.movieapp.TestDataSourceMovies
import com.anggitprayogo.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.Response
import java.net.ConnectException

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
class MovieUseCaseTest {

    private var movieRepo = mock(MovieRepository::class.java)
    private lateinit var SUT: MovieUseCase
    private val movieId = "1"

    @Before
    fun setUp() {
        SUT = MovieUseCase(movieRepo)
    }

    @Test
    fun `get popular movie should return success`() {
        val actual = ResultState.Success(TestDataSourceMovies.movies)
        val result = runBlocking {
            `when`(
                movieRepo.getPopularMovie()
            ).thenReturn(Response.success(TestDataSourceMovies.movies))
            SUT.getPopularMovie()
        }
        assert(actual == result)
    }

    @Test
    fun `get now playing movie should return success`() {
        val actual = ResultState.Success(TestDataSourceMovies.movies)
        val result = runBlocking {
            `when`(
                movieRepo.getNowPlayingMovie()
            ).thenReturn(Response.success(TestDataSourceMovies.movies))
            SUT.getNowPlayingMovie()
        }
        assert(actual == result)
    }

    @Test
    fun `get detail movie should return success`() {
        val actual = ResultState.Success(TestDataSourceMovies.movieDetail)
        val result = runBlocking {
            `when`(
                movieRepo.getDetailMovie(movieId)
            ).thenReturn(Response.success(TestDataSourceMovies.movieDetail))
            SUT.getMovieDetail(movieId)
        }
        assert(actual == result)
    }

    @Test
    fun `get favourite movie should return success`() {
        val actual = ResultState.Success(TestDataSourceMovies.moviesEntityList)
        val result = runBlocking {
            `when`(
                movieRepo.fetchAllMoviesDao()
            ).thenReturn(TestDataSourceMovies.moviesEntityList)
            SUT.getFavouriteMovie()
        }
        assert(actual == result)
    }

    @Test
    fun `get popular movie should return error "network exception error"`() {
        val actual = ResultState.NetworkError
        val result = runBlocking {
            given(movieRepo.getPopularMovie()).willAnswer {
                throw ConnectException()
            }
            SUT.getPopularMovie()
        }
        assert(actual == result)
    }
}