package com.anggitprayogo.movieapp.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anggitprayogo.core.base.BaseViewModel
import com.anggitprayogo.core.util.state.LoaderState
import com.anggitprayogo.core.util.state.ResultState
import com.anggitprayogo.core.util.thread.SchedulerProvider
import com.anggitprayogo.movieapp.data.remote.entity.Movie
import com.anggitprayogo.movieapp.data.enum.MovieFilter
import com.anggitprayogo.movieapp.domain.MovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
interface MainViewModelContract {
    fun getMovies(movieFilter: MovieFilter)
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

    init {
        getMovies(MovieFilter.POPULAR)
    }

    override fun getMovies(movieFilter: MovieFilter) {
        _state.value = LoaderState.ShowLoading
        launch {
            val result = useCase.getMoviesByType(movieFilter)
            withContext(Dispatchers.Main) {
                _state.value = LoaderState.HideLoading
                when (result) {
                    is ResultState.Success -> {
                        _resultMovies.postValue(result.data.results)
                    }
                    is ResultState.Error -> {
                        _error.postValue(result.error)
                    }
                    is ResultState.NetworkError -> {
                        _networkError.postValue(true)
                    }
                }
            }
        }
    }
}