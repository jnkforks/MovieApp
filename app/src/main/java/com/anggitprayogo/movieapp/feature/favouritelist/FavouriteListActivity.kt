package com.anggitprayogo.movieapp.feature.favouritelist

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggitprayogo.core.base.BaseActivity
import com.anggitprayogo.core.util.ext.setGone
import com.anggitprayogo.core.util.ext.setVisible
import com.anggitprayogo.core.util.ext.toast
import com.anggitprayogo.movieapp.BaseApplication
import com.anggitprayogo.movieapp.R
import com.anggitprayogo.movieapp.data.db.entity.MovieEntity
import kotlinx.android.synthetic.main.activity_favourite_list.*
import javax.inject.Inject

class FavouriteListActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FavouriteListViewModel

    private val movieList = mutableListOf<MovieEntity>()

    private val adapter: FavouriteListAdapter by lazy {
        FavouriteListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_list)
        initViewModel()
        initObserver()
        initListener()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rvMovie.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvMovie.adapter = adapter
        adapter.setActivity(this)
    }

    private fun initListener() {
        ivBack.setOnClickListener {
            finish()
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchAllMovies()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initObserver() {
        viewModel.resultMovies.observe(this, Observer {
            it?.let {
                handleStateResultMovies(it)
            }
        })

        viewModel.error.observe(this, Observer {
            toast(it)
        })
    }

    private fun handleStateResultMovies(movies: List<MovieEntity>) {
        handleEmptyViewVisibility(movies)
        movieList.clear()
        movieList.addAll(movies)
        adapter.setItems(movieList)
    }

    private fun handleEmptyViewVisibility(movies: List<MovieEntity>) {
        if (movies.isEmpty()) {
            rvMovie.setGone()
            viewEmpty.setVisible()
        }else{
            rvMovie.setVisible()
            viewEmpty.setGone()
        }
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)[FavouriteListViewModel::class.java]
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.fetchAllMovies()
    }

    override fun initInjector() {
        (application as BaseApplication).appComponent.inject(this)
    }
}