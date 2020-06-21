package com.anggitprayogo.movieapp.feature.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.anggitprayogo.core.util.state.ResultState
import com.anggitprayogo.core.util.thread.TestSchedulerProvider
import com.anggitprayogo.movieapp.TestDataSourceMovies
import com.anggitprayogo.movieapp.data.entity.Movie
import com.anggitprayogo.movieapp.domain.MovieUseCase
import com.anggitprayogo.movieapp.getResponseErrorValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    lateinit var popularMovies: Observer<List<Movie>>

    @Mock
    lateinit var upcomingMovies: Observer<List<Movie>>

    @Mock
    lateinit var topRatedMovies: Observer<List<Movie>>

    @Mock
    lateinit var nowPlayingMovies: Observer<List<Movie>>

    @Mock
    lateinit var error: Observer<String>

    @Mock
    lateinit var network: Observer<Boolean>

    @Captor
    lateinit var argResultCaptor: ArgumentCaptor<List<Movie>>

    @Captor
    lateinit var argNetworkCaptor: ArgumentCaptor<Boolean>

    @Captor
    lateinit var argErrorCaptor: ArgumentCaptor<String>


    @Mock
    lateinit var useCase: MovieUseCase
    private val schedulerProvider = TestSchedulerProvider()
    private lateinit var SUT: MainViewModel


    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(schedulerProvider.ui())

        SUT = MainViewModel(useCase, schedulerProvider)

        observeMovies()
    }

    private fun observeMovies() {
        SUT.resultPopularMovie.observeForever(popularMovies)
        SUT.resultUpcomingMovie.observeForever(upcomingMovies)
        SUT.resultTopRatedMovie.observeForever(topRatedMovies)
        SUT.resultNowPlaying.observeForever(nowPlayingMovies)
        SUT.error.observeForever(error)
        SUT.networkError.observeForever(network)
    }

    @Test
    fun `get popular movies should return response of correct movies popular`() = runBlocking {
        //given
        val response = ResultState.Success(TestDataSourceMovies.movies)

        //when
        `when`(useCase.getPopularMovie())
            .thenReturn(response)
        SUT.getPopularMovie()

        //assert
        verify(popularMovies, atLeastOnce()).onChanged(argResultCaptor.capture())
        assertEquals(response.data.results, argResultCaptor.allValues.first())
        clearInvocations(useCase, popularMovies)
    }

    @Test
    fun `get top rated movies should return response of correct movies top rated`() = runBlocking {
        //given
        val response = ResultState.Success(TestDataSourceMovies.movies)

        //when
        `when`(useCase.getTopRatedMovie())
            .thenReturn(response)
        SUT.getTopRatedMovie()

        //assert
        verify(topRatedMovies, atLeastOnce()).onChanged(argResultCaptor.capture())
        assertEquals(response.data.results, argResultCaptor.allValues.first())
        clearInvocations(useCase, topRatedMovies)
    }

    @Test
    fun `get upcoming movies should return response of correct movies upcoming`() = runBlocking {
        //given
        val response = ResultState.Success(TestDataSourceMovies.movies)

        //when
        `when`(useCase.getUpcomingMovie())
            .thenReturn(response)
        SUT.getUpcomingMovie()

        //assert
        verify(upcomingMovies, atLeastOnce()).onChanged(argResultCaptor.capture())
        assertEquals(response.data.results, argResultCaptor.allValues.first())
        clearInvocations(useCase, upcomingMovies)
    }

    @Test
    fun `get now playing movies should return response of correct movies now playing`() = runBlocking {
        //given
        val response = ResultState.Success(TestDataSourceMovies.movies)

        //when
        `when`(useCase.getNowPlayingMovie())
            .thenReturn(response)
        SUT.getPlayingNowMovie()

        //assert
        verify(nowPlayingMovies, atLeastOnce()).onChanged(argResultCaptor.capture())
        assertEquals(response.data.results, argResultCaptor.allValues.first())
        clearInvocations(useCase, nowPlayingMovies)
    }


    @Test
    fun `get movies should return error`() = runBlocking {
        //given
        val response = ResultState.Error("Error", getResponseErrorValue())

        //when
        `when`(useCase.getPopularMovie()).thenReturn(response)
        SUT.getPopularMovie()

        //assert
        verify(error, atLeastOnce()).onChanged(argErrorCaptor.capture())
        assertEquals(response.error, argErrorCaptor.allValues.first())
        clearInvocations(useCase, error)
    }

    @Test
    fun `get movies should return error network`() = runBlocking {
        //given
        val returnValue = ResultState.NetworkError
        val expected = true

        //when
        `when`(useCase.getPopularMovie()).thenReturn(returnValue)
        SUT.getPopularMovie()

        //assert
        verify(network, atLeastOnce()).onChanged(argNetworkCaptor.capture())
        assertEquals(expected, argNetworkCaptor.allValues.first())
        clearInvocations(useCase, network)
    }
}