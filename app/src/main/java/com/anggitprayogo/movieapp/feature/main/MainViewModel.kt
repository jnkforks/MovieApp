package com.anggitprayogo.movieapp.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.anggitprayogo.core.base.BaseViewModel
import com.anggitprayogo.core.util.state.LoaderState
import com.anggitprayogo.core.util.thread.SchedulerProvider
import com.anggitprayogo.movieapp.data.enum.MovieFilter
import com.anggitprayogo.movieapp.data.local.entity.MovieEntity
import com.anggitprayogo.movieapp.data.remote.entity.Movie
import com.anggitprayogo.movieapp.domain.MovieUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
interface MainViewModelContract {
    fun getMovies(movieFilter: MovieFilter): Flow<PagingData<MovieEntity>>
}

class MainViewModel @Inject constructor(
    private val useCase: MovieUseCase,
    dispatcher: SchedulerProvider
) : BaseViewModel(dispatcher), MainViewModelContract {

    /**
     * Loader State
     */
    private val _state = MutableLiveData<LoaderState>()
    val state: LiveData<LoaderState>
        get() = _state

    /**
     * Popular Movie State
     */
    private val _resultMovies = MutableLiveData<List<Movie>>()
    val resultMovies: LiveData<List<Movie>>
        get() = _resultMovies

    /**
     * Paging Source
     */
    private var currentSearchResult: Flow<PagingData<MovieEntity>>? = null

    /**
     * Error
     */
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error


    /**
     * Network Error
     */
    private val _networkError = MutableLiveData<Boolean>()
    val networkError: LiveData<Boolean>
        get() = _networkError

    private var currentFilter: MovieFilter? = null

    override fun getMovies(movieFilter: MovieFilter): Flow<PagingData<MovieEntity>> {
        val lastResult = currentSearchResult
        if (currentFilter == movieFilter && lastResult != null) return lastResult
        currentFilter = movieFilter
        val newResult = useCase.getMoviesByTypePageSource(movieFilter).cachedIn(this)
        currentSearchResult = newResult
        return newResult
    }
}