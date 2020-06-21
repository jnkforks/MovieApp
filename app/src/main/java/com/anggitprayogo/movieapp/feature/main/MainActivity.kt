package com.anggitprayogo.movieapp.feature.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggitprayogo.core.base.BaseActivity
import com.anggitprayogo.core.util.ext.setGone
import com.anggitprayogo.core.util.ext.setVisible
import com.anggitprayogo.core.util.ext.toast
import com.anggitprayogo.core.util.state.LoaderState
import com.anggitprayogo.movieapp.BaseApplication
import com.anggitprayogo.movieapp.R
import com.anggitprayogo.movieapp.data.entity.Movie
import com.anggitprayogo.movieapp.data.enum.MovieFilter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), FilterBottomSheetDialogFragment.ItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainViewModel

    private var movieList = mutableListOf<Movie>()

    private val mainAdapter: MainAdapter by lazy {
        MainAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInjector()
        initViewModel()
        initRecyclerview()
        initObserver()
        initListener()
    }

    private fun initListener() {
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getPopularMovie()
            swipeRefreshLayout.isRefreshing = false
        }

        cvFilter.setOnClickListener {
            showFilterBottomSheet()
        }
    }

    private fun initObserver() {
        viewModel.state.observe(this, Observer {
            it?.let {
                handleStateLoading(it)
            }
        })

        viewModel.resultPopularMovie.observe(this, Observer {
            it?.let {
                handleStatePopularMovie(it)
            }
        })

        viewModel.resultNowPlaying.observe(this, Observer {
            it?.let {
                handleStatePopularMovie(it)
            }
        })

        viewModel.resultTopRatedMovie.observe(this, Observer {
            it?.let {
                handleStatePopularMovie(it)
            }
        })

        viewModel.resultUpcomingMovie.observe(this, Observer {
            it?.let {
                handleStatePopularMovie(it)
            }
        })

        viewModel.networkError.observe(this, Observer {
            it?.let {
                handleStateInternet(it)
            }
        })
    }

    private fun handleStateInternet(error: Boolean) {
        if (error) {
            viewLoading.setGone()
            viewErrorConnection.setVisible()
            rvMovie.setGone()
        } else {
            viewLoading.setGone()
            viewErrorConnection.setGone()
            rvMovie.setVisible()
        }
    }

    private fun handleStatePopularMovie(results: List<Movie>) {
        movieList.clear()
        movieList.addAll(results)
        mainAdapter.setItems(movieList)
    }

    private fun handleStateLoading(loading: LoaderState) {
        if (loading is LoaderState.ShowLoading) {
            viewLoading.setVisible()
            viewErrorConnection.setGone()
            rvMovie.setGone()
        } else {
            viewLoading.setGone()
            viewErrorConnection.setGone()
            rvMovie.setVisible()
        }
    }

    private fun initRecyclerview() {
        rvMovie.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvMovie.adapter = mainAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
    }

    private fun showFilterBottomSheet() {
        val bottomSheet = FilterBottomSheetDialogFragment().newInstance()
        bottomSheet?.show(supportFragmentManager, FilterBottomSheetDialogFragment.TAG)
    }

    override fun initInjector() {
        (application as BaseApplication).appComponent.inject(this)
    }

    override fun onItemClick(item: MovieFilter?) {
        viewModel.getMovie(item ?: MovieFilter.POPULAR)
    }
}