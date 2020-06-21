package com.anggitprayogo.movieapp.feature.detail

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.anggitprayogo.core.base.BaseActivity
import com.anggitprayogo.core.util.viewmodel.ViewModelFactory
import com.anggitprayogo.movieapp.BaseApplication
import com.anggitprayogo.movieapp.R
import javax.inject.Inject

class MovieDetailActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MovieDetailViewModel

    private var movieId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        handleIntent()
        initViewModel()
        initObserver()
        initListener()
        fetchData()
    }

    private fun handleIntent() {
        movieId = intent.getStringExtra(MOVIE_ID_KEY)
    }

    private fun fetchData() {
        movieId?.let { viewModel.getMovieDetail(it) }
    }

    private fun initListener() {

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MovieDetailViewModel::class.java]
    }

    private fun initObserver() {

    }

    override fun initInjector() {
        (application as BaseApplication).appComponent.inject(this)
    }

    companion object {
        const val MOVIE_ID_KEY = "movie_id_key"
    }
}