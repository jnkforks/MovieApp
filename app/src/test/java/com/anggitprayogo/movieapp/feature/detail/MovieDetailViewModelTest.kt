package com.anggitprayogo.movieapp.feature.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.anggitprayogo.core.util.state.ResultState
import com.anggitprayogo.core.util.thread.TestSchedulerProvider
import com.anggitprayogo.movieapp.TestDataSourceMovies
import com.anggitprayogo.movieapp.data.local.entity.FavouriteEntity
import com.anggitprayogo.movieapp.data.remote.entity.MovieDetail
import com.anggitprayogo.movieapp.data.remote.entity.MovieReviews
import com.anggitprayogo.movieapp.domain.MovieUseCase
import com.anggitprayogo.movieapp.getResponseErrorValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
class MovieDetailViewModelTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    lateinit var movie: Observer<MovieDetail>

    @Mock
    lateinit var reviews: Observer<MovieReviews>

    @Mock
    lateinit var favouriteListDb: Observer<List<FavouriteEntity>>

    @Mock
    lateinit var error: Observer<String>

    @Mock
    lateinit var network: Observer<Boolean>

    @Captor
    lateinit var argResultCaptor: ArgumentCaptor<MovieDetail>

    @Captor
    lateinit var argResultReviewsCaptor: ArgumentCaptor<MovieReviews>

    @Captor
    lateinit var argResultFavouriteListDbCaptor: ArgumentCaptor<List<FavouriteEntity>>

    @Captor
    lateinit var argNetworkCaptor: ArgumentCaptor<Boolean>

    @Captor
    lateinit var argErrorCaptor: ArgumentCaptor<String>


    @Mock
    lateinit var useCase: MovieUseCase
    private val schedulerProvider = TestSchedulerProvider()
    private lateinit var SUT: MovieDetailViewModel

    private val movieId = "1"

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(schedulerProvider.ui())

        SUT = MovieDetailViewModel(useCase, schedulerProvider)

        observeMovie()
    }

    private fun observeMovie() {
        SUT.resultDetailMovie.observeForever(movie)
        SUT.resultReviews.observeForever(reviews)
        SUT.resultDetailFromDb.observeForever(favouriteListDb)
        SUT.error.observeForever(error)
        SUT.networkError.observeForever(network)
    }

    @Test
    fun `get movie by id should return response of correct movie`() = runBlocking {
        //given
        val response = ResultState.Success(TestDataSourceMovies.movieDetail)

        //when
        `when`(useCase.getMovieDetail(movieId))
            .thenReturn(response)
        SUT.getMovieDetail(movieId)

        //assert
        verify(movie, atLeastOnce()).onChanged(argResultCaptor.capture())
        Assert.assertEquals(response.data, argResultCaptor.allValues.first())
        clearInvocations(useCase, movie)
    }

    @Test
    fun `get reviews by id should return response of correct reviews`() = runBlocking {
        //given
        val response = ResultState.Success(TestDataSourceMovies.movieReviews)

        //when
        `when`(useCase.getMovieReviewsByMovieId(movieId))
            .thenReturn(response)
        SUT.getReviewsByMovieId(movieId)

        //assert
        verify(reviews, atLeastOnce()).onChanged(argResultReviewsCaptor.capture())
        Assert.assertEquals(response.data, argResultReviewsCaptor.allValues.first())
        clearInvocations(useCase, reviews)
    }

    @Test
    fun `get movie from db by id should return response of correct movie`() = runBlocking {
        //given
        val response = ResultState.Success(TestDataSourceMovies.moviesEntityList)

        //when
        `when`(useCase.getMovieDetailByMovieId(movieId.toInt()))
            .thenReturn(response)
        SUT.getMovieDetailDb(movieId)

        //assert
        verify(favouriteListDb, atLeastOnce()).onChanged(argResultFavouriteListDbCaptor.capture())
        Assert.assertEquals(response.data, argResultFavouriteListDbCaptor.allValues.first())
        clearInvocations(useCase, favouriteListDb)
    }

    @Test
    fun `get movie by id should return error`() = runBlocking {
        //given
        val response = ResultState.Error("Error", getResponseErrorValue())

        //when
        `when`(useCase.getMovieDetail(movieId)).thenReturn(response)
        SUT.getMovieDetail(movieId)

        //assert
        verify(error, atLeastOnce()).onChanged(argErrorCaptor.capture())
        Assert.assertEquals(response.error, argErrorCaptor.allValues.first())
        clearInvocations(useCase, error)
    }

    @Test
    fun `get movie by id should return error network`() = runBlocking {
        //given
        val returnValue = ResultState.NetworkError
        val expected = true

        //when
        `when`(useCase.getMovieDetail(movieId)).thenReturn(returnValue)
        SUT.getMovieDetail(movieId)

        //assert
        verify(network, atLeastOnce()).onChanged(argNetworkCaptor.capture())
        Assert.assertEquals(expected, argNetworkCaptor.allValues.first())
        clearInvocations(useCase, network)
    }
}