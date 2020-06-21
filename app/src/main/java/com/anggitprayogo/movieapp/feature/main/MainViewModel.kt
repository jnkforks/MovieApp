package com.anggitprayogo.movieapp.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anggitprayogo.core.base.BaseViewModel
import com.anggitprayogo.core.util.state.LoaderState
import com.anggitprayogo.core.util.state.ResultState
import com.anggitprayogo.core.util.thread.SchedulerProvider
import com.anggitprayogo.movieapp.data.entity.Movie
import com.anggitprayogo.movieapp.domain.MovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
interface MainContract {
    fun getPopularMovie()
    fun getTopRatedMovie()
    fun getPlayingNowMovie()
    fun getUpcomingMovie()
}

class MainViewModel @Inject constructor(
    private val useCase: MovieUseCase,
    dispatcher: SchedulerProvider
) : BaseViewModel(dispatcher), MainContract {

    /**
     * Loader State
     */
    private val _state = MutableLiveData<LoaderState>()
    val state: LiveData<LoaderState>
        get() = _state

    /**
     * Popular Movie State
     */
    private val _resultPopularMovie = MutableLiveData<List<Movie>>()
    val resultPopularMovie: LiveData<List<Movie>>
        get() = _resultPopularMovie

    /**
     * Top Rated Movie State
     */
    private val _resultTopRatedMovie = MutableLiveData<List<Movie>>()
    val resultTopRatedMovie: LiveData<List<Movie>>
        get() = _resultTopRatedMovie

    /**
     * Upcoming Movie State
     */
    private val _resultUpcomingMovie = MutableLiveData<List<Movie>>()
    val resultUpcomingMovie: LiveData<List<Movie>>
        get() = _resultUpcomingMovie

    /**
     * Now Playing Movie State
     */
    private val _resultNowPlaying = MutableLiveData<List<Movie>>()
    val resultNowPlaying: LiveData<List<Movie>>
        get() = _resultNowPlaying


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
        getPopularMovie()
    }

    override fun getPopularMovie() {
        _state.value = LoaderState.ShowLoading
        launch {
            val result = useCase.getPopularMovie()
            withContext(Dispatchers.Main) {
                _state.value = LoaderState.HideLoading
                when (result) {
                    is ResultState.Success -> {
                        _resultPopularMovie.postValue(result.data.results)
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

    override fun getTopRatedMovie() {
        _state.value = LoaderState.ShowLoading
        launch {
            val result = useCase.getTopRatedMovie()
            withContext(Dispatchers.Main) {
                _state.value = LoaderState.HideLoading
                when (result) {
                    is ResultState.Success -> {
                        _resultTopRatedMovie.postValue(result.data.results)
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

    override fun getPlayingNowMovie() {
        _state.value = LoaderState.ShowLoading
        launch {
            val result = useCase.getNowPlayingMovie()
            withContext(Dispatchers.Main) {
                _state.value = LoaderState.HideLoading
                when (result) {
                    is ResultState.Success -> {
                        _resultNowPlaying.postValue(result.data.results)
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

    override fun getUpcomingMovie() {
        _state.value = LoaderState.ShowLoading
        launch {
            val result = useCase.getUpcomingMovie()
            withContext(Dispatchers.Main) {
                _state.value = LoaderState.HideLoading
                when (result) {
                    is ResultState.Success -> {
                        _resultUpcomingMovie.postValue(result.data.results)
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