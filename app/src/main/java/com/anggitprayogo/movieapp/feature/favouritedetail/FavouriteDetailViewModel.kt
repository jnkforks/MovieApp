package com.anggitprayogo.movieapp.feature.favouritedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anggitprayogo.core.base.BaseViewModel
import com.anggitprayogo.core.util.state.ResultState
import com.anggitprayogo.core.util.thread.SchedulerProvider
import com.anggitprayogo.movieapp.data.db.entity.MovieEntity
import com.anggitprayogo.movieapp.domain.MovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
interface FavouriteDetailContract {
    fun getMovieDetailDb(movieId: String)
    fun insertMovieToDb(movieEntity: MovieEntity)
    fun deleteMovieFromDb(movieEntity: MovieEntity)
}

class FavouriteDetailViewModel @Inject constructor(
    private val useCase: MovieUseCase,
    dispatcher: SchedulerProvider
) : BaseViewModel(dispatcher), FavouriteDetailContract{

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
    private val _resultDetailFromDb = MutableLiveData<List<MovieEntity>>()
    val resultDetailFromDb: LiveData<List<MovieEntity>>
        get() = _resultDetailFromDb

    /**
     * Error
     */
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error


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

    override fun insertMovieToDb(movieEntity: MovieEntity) {
        launch {
            try {
                useCase.insertMovieToDb(movieEntity)
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

    override fun deleteMovieFromDb(movieEntity: MovieEntity) {
        launch {
            try {
                useCase.deleteMovieFromDb(movieEntity)
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