package com.anggitprayogo.movieapp.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anggitprayogo.core.base.BaseViewModel
import com.anggitprayogo.core.util.state.LoaderState
import com.anggitprayogo.core.util.state.ResultState
import com.anggitprayogo.core.util.thread.SchedulerProvider
import com.anggitprayogo.movieapp.data.local.entity.FavouriteEntity
import com.anggitprayogo.movieapp.data.remote.entity.MovieDetail
import com.anggitprayogo.movieapp.data.remote.entity.MovieReviews
import com.anggitprayogo.movieapp.domain.MovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
interface MovieDetailContract {
    fun getMovieDetail(movieId: String)
    fun getMovieDetailDb(movieId: String)
    fun getReviewsByMovieId(movieId: String)
    fun insertMovieToDb(favouriteEntity: FavouriteEntity)
    fun deleteMovieFromDb(favouriteEntity: FavouriteEntity)
}

class MovieDetailViewModel @Inject constructor(
    private val useCase: MovieUseCase,
    dispatcher: SchedulerProvider
) : BaseViewModel(dispatcher), MovieDetailContract {

    /**
     * Loader State
     */
    private val _state = MutableLiveData<LoaderState>()
    val state: LiveData<LoaderState>
        get() = _state

    /**
     * Detail Movie
     */
    private val _resultDetailMovie = MutableLiveData<MovieDetail>()
    val resultDetailMovie: LiveData<MovieDetail>
        get() = _resultDetailMovie

    /**
     * Reviews
     */
    private val _resultReviews = MutableLiveData<MovieReviews>()
    val resultReviews: LiveData<MovieReviews>
        get() = _resultReviews

    /**
     * Insert movie
     */
    private val _resultInsertMovieToDb = MutableLiveData<Boolean>()
    val resultInsertMovieToDb: LiveData<Boolean>
        get() = _resultInsertMovieToDb

    /**
     * Delete Movie
     */
    private val _resultDeleteMovieFromDb = MutableLiveData<Boolean>()
    val resultDeleteMovieFromDb: LiveData<Boolean>
        get() = _resultDeleteMovieFromDb

    /**
     * Movie Detail from db
     */
    private val _resultDetailFromDb = MutableLiveData<List<FavouriteEntity>>()
    val resultDetailFromDb: LiveData<List<FavouriteEntity>>
        get() = _resultDetailFromDb

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

    override fun getMovieDetail(movieId: String) {
        _state.value = LoaderState.ShowLoading
        launch {
            val result = useCase.getMovieDetail(movieId)
            withContext(Dispatchers.Main) {
                _state.value = LoaderState.HideLoading
                when (result) {
                    is ResultState.Success -> {
                        _resultDetailMovie.postValue(result.data)
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

    override fun getReviewsByMovieId(movieId: String) {
        _state.value = LoaderState.ShowLoading
        launch {
            val result = useCase.getMovieReviewsByMovieId(movieId)
            withContext(Dispatchers.Main) {
                _state.value = LoaderState.HideLoading
                when (result) {
                    is ResultState.Success -> _resultReviews.postValue(result.data)
                    is ResultState.Error -> _error.postValue(result.error)
                    is ResultState.NetworkError -> _networkError.postValue(true)
                }
            }
        }
    }

    override fun getMovieDetailDb(movieId: String) {
        launch {
            val result = useCase.getMovieDetailByMovieId(movieId.toInt())
            withContext(Dispatchers.Main) {
                when (result) {
                    is ResultState.Success -> _resultDetailFromDb.postValue(result.data)
                    is ResultState.Error -> _error.postValue(result.error)
                }
            }
        }
    }

    override fun insertMovieToDb(favouriteEntity: FavouriteEntity) {
        launch {
            try {
                useCase.insertMovieToDb(favouriteEntity)
                withContext(Dispatchers.Main) {
                    _resultInsertMovieToDb.postValue(true)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.postValue(e.localizedMessage)
                }
            }
        }
    }

    override fun deleteMovieFromDb(favouriteEntity: FavouriteEntity) {
        launch {
            try {
                useCase.deleteMovieFromDb(favouriteEntity)
                withContext(Dispatchers.Main) {
                    _resultDeleteMovieFromDb.postValue(true)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _error.postValue(e.localizedMessage)
                }
            }
        }
    }
}