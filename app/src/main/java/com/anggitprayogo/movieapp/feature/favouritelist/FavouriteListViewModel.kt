package com.anggitprayogo.movieapp.feature.favouritelist

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
import javax.inject.Inject

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
interface FavouriteListContract {
    fun fetchAllMovies()
}

class FavouriteListViewModel @Inject constructor(
    private val useCase: MovieUseCase,
    dispatcher: SchedulerProvider
) : BaseViewModel(dispatcher), FavouriteListContract {

    /**
     * Movie State
     */
    private val _resultMovies = MutableLiveData<List<MovieEntity>>()
    val resultMovies: LiveData<List<MovieEntity>>
        get() = _resultMovies

    /**
     * Error
     */
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        fetchAllMovies()
    }

    override fun fetchAllMovies() {
        launch {
            val result = useCase.getFavouriteMovie()
            withContext(Dispatchers.Main) {
                when (result) {
                    is ResultState.Success -> _resultMovies.postValue(result.data)
                    is ResultState.Error -> _error.postValue(result.error)
                }
            }
        }
    }
}
