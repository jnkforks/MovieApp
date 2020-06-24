package com.anggitprayogo.movieapp.feature.favouritelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.anggitprayogo.core.util.state.ResultState
import com.anggitprayogo.core.util.thread.TestSchedulerProvider
import com.anggitprayogo.movieapp.TestDataSourceMovies
import com.anggitprayogo.movieapp.data.local.entity.FavouriteEntity
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

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
class FavouriteListViewModelTest{

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    lateinit var favouriteMovies: Observer<List<FavouriteEntity>>

    @Mock
    lateinit var error: Observer<String>

    @Captor
    lateinit var argResultCaptor: ArgumentCaptor<List<FavouriteEntity>>

    @Captor
    lateinit var argErrorCaptor: ArgumentCaptor<String>


    @Mock
    lateinit var useCase: MovieUseCase
    private val schedulerProvider = TestSchedulerProvider()
    private lateinit var SUT: FavouriteListViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(schedulerProvider.ui())

        SUT = FavouriteListViewModel(useCase, schedulerProvider)

        observeFavouriteMovies()
    }

    private fun observeFavouriteMovies() {
        SUT.resultMovies.observeForever(favouriteMovies)
        SUT.error.observeForever(error)
    }

    @Test
    fun `get favourite movies should return response of correct movie favourite`() = runBlocking {
        //given
        val response = ResultState.Success(TestDataSourceMovies.moviesEntityList)

        //when
        Mockito.`when`(useCase.getFavouriteMovie())
            .thenReturn(response)
        SUT.fetchAllMovies()

        //assert
        Mockito.verify(favouriteMovies, Mockito.atLeastOnce()).onChanged(argResultCaptor.capture())
        Assert.assertEquals(response.data, argResultCaptor.allValues.first())
        Mockito.clearInvocations(useCase, favouriteMovies)
    }

    @Test
    fun `get favourite should return error`() = runBlocking {
        //given
        val response = ResultState.Error("Error", getResponseErrorValue())

        //when
        Mockito.`when`(useCase.getFavouriteMovie()).thenReturn(response)
        SUT.fetchAllMovies()

        //assert
        Mockito.verify(error, Mockito.atLeastOnce()).onChanged(argErrorCaptor.capture())
        Assert.assertEquals(response.error, argErrorCaptor.allValues.first())
        Mockito.clearInvocations(useCase, error)
    }
}