package com.anggitprayogo.movieapp.feature.main

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggitprayogo.core.base.BaseActivity
import com.anggitprayogo.core.util.ext.setGone
import com.anggitprayogo.core.util.ext.setVisible
import com.anggitprayogo.core.util.state.LoaderState
import com.anggitprayogo.movieapp.BaseApplication
import com.anggitprayogo.movieapp.R
import com.anggitprayogo.movieapp.data.enum.MovieFilter
import com.anggitprayogo.movieapp.feature.favouritelist.FavouriteListActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.base_error_internet_connection.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainActivity : BaseActivity(), FilterBottomSheetDialogFragment.ItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainViewModel

    private var currentFilter = MovieFilter.POPULAR

    private var job: Job? = null

    private val adapter: MainPagingAdapter by lazy {
        MainPagingAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initInjector()
        initViewModel()
        initRecyclerview()
        initObserver()
        initListener()
        fetchData()
        initFetchData()
    }

    private fun fetchData() {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getMovies(currentFilter).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun initFetchData() {
        lifecycleScope.launch {
            adapter.dataRefreshFlow.collect {
                rvMovie.scrollToPosition(0)
            }
        }
    }

    private fun initListener() {
        swipeRefreshLayout.setOnRefreshListener {
            fetchData()
            swipeRefreshLayout.isRefreshing = false
        }

        cvFilter.setOnClickListener {
            showFilterBottomSheet()
        }

        ivFavourite.setOnClickListener {
            startActivity(Intent(this, FavouriteListActivity::class.java))
        }

        btnRetryLoad.setOnClickListener { adapter.retry() }
    }

    private fun initObserver() {
        viewModel.state.observe(this, Observer {
            it?.let {
                handleStateLoading(it)
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
        rvMovie.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { adapter.retry() },
            footer = MovieLoadStateAdapter { adapter.retry() }
        )
        adapter.setActivity(this)
        adapter.addLoadStateListener { loadState ->
            rvMovie.isVisible = loadState.refresh is LoadState.NotLoading
            viewLoading.isVisible = loadState.refresh is LoadState.Loading
            viewErrorConnection.isVisible = loadState.refresh is LoadState.Error

//            val errorState = loadState.source.append as? LoadState.Error
//                ?: loadState.source.prepend as? LoadState.Error
//                ?: loadState.append as? LoadState.Error
//                ?: loadState.prepend as? LoadState.Error

//            errorState?.let {
//                Toast.makeText(
//                    this,
//                    "\uD83D\uDE28 Wooops ${it.error}",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
        }
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
        val filterSelected = item ?: MovieFilter.POPULAR
        currentFilter = filterSelected
        fetchData()
        changeAppTitleByFilter(filterSelected)
    }

    private fun changeAppTitleByFilter(filterSelected: MovieFilter) {
        val appBarTitle = when (filterSelected) {
            MovieFilter.POPULAR -> getString(R.string.popular_movie)
            MovieFilter.NOW_PLAYING -> getString(R.string.now_playing)
            MovieFilter.UP_COMING -> getString(R.string.upcoming)
            MovieFilter.TOP_RATED -> getString(R.string.top_rated)
        }
        tvAppName.text = appBarTitle
    }
}