package com.anggitprayogo.movieapp.feature.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.anggitprayogo.core.base.BaseActivity
import com.anggitprayogo.core.util.ext.load
import com.anggitprayogo.movieapp.BaseApplication
import com.anggitprayogo.movieapp.R
import com.anggitprayogo.movieapp.data.entity.MovieDetail
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_movie_detail.*
import javax.inject.Inject


class MovieDetailActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MovieDetailViewModel

    private var movieDetail: MovieDetail? = null

    private var movieId: String? = null

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        handleIntent()
        initViewModel()
        initObserver()
        initListener()
        fetchData()
        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBar: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if ((scrollRange + verticalOffset) == 0) {
                    isShow = true
                    showMenuFavourite(R.id.action_favourite);
                } else if (isShow) {
                    isShow = false
                    hideMenuFavourite(R.id.action_favourite);
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_detail, menu)
        hideMenuFavourite(R.id.action_favourite)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_favourite) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    @Suppress("SameParameterValue")
    private fun hideMenuFavourite(id: Int) {
        val item = menu?.findItem(id)
        item?.isVisible = false
        setActionBarTitle(null)
    }

    @Suppress("SameParameterValue")
    private fun showMenuFavourite(id: Int) {
        val item = menu?.findItem(id)
        item?.isVisible = true
        setActionBarTitle(movieDetail?.title)
    }

    private fun setActionBarTitle(title: String?) {
        supportActionBar?.title = title
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
        viewModel.resultDetailMovie.observe(this, Observer {
            it?.let {
                handleStateMovieDetail(it)
            }
        })
    }

    private fun handleStateMovieDetail(movie: MovieDetail) {
        movieDetail = movie
        ivBannerMovie.load(movie.getBannerMovie())
        tvMovieMetaData.text = movie.getMetaData()
        tvMovieTitle.text = movie.originalTitle
        tvImdbRating.text = getString(
            R.string.imdb_rating_template,
            (movie.getImdbRating()?.toFloat() ?: 0.0).toString()
        )
        ratingMovie.rating = movie.getImdbRating()?.toFloat() ?: 0f
        tvOverview.text = movie.overview
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun initInjector() {
        (application as BaseApplication).appComponent.inject(this)
    }

    companion object {
        const val MOVIE_ID_KEY = "movie_id_key"
    }
}