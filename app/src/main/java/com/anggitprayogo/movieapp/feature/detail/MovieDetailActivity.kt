package com.anggitprayogo.movieapp.feature.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggitprayogo.core.base.BaseActivity
import com.anggitprayogo.core.util.ext.load
import com.anggitprayogo.core.util.ext.setGone
import com.anggitprayogo.core.util.ext.setVisible
import com.anggitprayogo.core.util.ext.toast
import com.anggitprayogo.movieapp.BaseApplication
import com.anggitprayogo.movieapp.R
import com.anggitprayogo.movieapp.data.db.entity.MovieEntity
import com.anggitprayogo.movieapp.data.entity.MovieDetail
import com.anggitprayogo.movieapp.data.entity.MovieReviews
import com.anggitprayogo.movieapp.data.entity.Review
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_movie_detail.*
import javax.inject.Inject


class MovieDetailActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MovieDetailViewModel

    private var movieDetail: MovieDetail? = null

    private var movieEntity: MovieEntity? = null

    private var movieId: String? = null

    private var menu: Menu? = null

    private var favouriteActive = false

    private val reviewsAdapter: ReviewsAdapter by lazy {
        ReviewsAdapter()
    }

    private var reviewsList = mutableListOf<Review>()

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        handleIntent()
        initViewModel()
        initObserver()
        initListener()
        initRecyclerViewReviews()
        fetchData()
        initToolbar()
    }

    private fun initRecyclerViewReviews() {
        rvReviews.adapter = reviewsAdapter
        rvReviews.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvReviews.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
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
        movieId?.let {
            viewModel.getMovieDetail(it)
            viewModel.getReviewsByMovieId(it)
            viewModel.getMovieDetailDb(it)
        }
    }

    private fun initListener() {
        fabFavourite.setOnClickListener {
            if (favouriteActive) {
                movieEntity?.let { it1 -> viewModel.deleteMovieFromDb(it1) }
            } else {
                val movieEntity = MovieEntity(
                    movieId = movieId?.toInt(),
                    title = movieDetail?.title,
                    genres = movieDetail?.getMetaData(),
                    vote = movieDetail?.getImdbRating(),
                    releaseDate = movieDetail?.releaseDate,
                    overview = movieDetail?.overview,
                    bannerUrl = movieDetail?.getBannerMovie(),
                    posterUrl = movieDetail?.getPosterMovie()
                )
                viewModel.insertMovieToDb(movieEntity)
            }
        }
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

        viewModel.resultReviews.observe(this, Observer {
            it?.let {
                handleStateReviews(it)
            }
        })

        viewModel.resultDetailFromDb.observe(this, Observer {
            handleStateMovieDetailFromDb(it)
        })

        viewModel.resultInsertMovieToDb.observe(this, Observer {
            if (it) {
                movieId?.let { viewModel.getMovieDetailDb(it) }
                toast(getString(R.string.message_success_add_movie_from_favourite))
            }
        })

        viewModel.resultDeleteMovieFromDb.observe(this, Observer {
            if (it) {
                movieId?.let { viewModel.getMovieDetailDb(it) }
                toast(getString(R.string.message_success_remove_movie_from_favourite))
            }
        })

        viewModel.networkError.observe(this, Observer {
            it?.let {
                handleStateNetworkError(it)
            }
        })

        viewModel.error.observe(this, Observer {
            toast(it)
        })
    }

    private fun handleStateReviews(reviews: MovieReviews) {
        handleReviewEmptyState(reviews)
        tvReviewTotal.text = reviews.results.size.toString()
        reviewsList.clear()
        reviewsList.addAll(reviews.results.toMutableList())
        reviewsAdapter.setItems(reviewsList)
    }

    private fun handleReviewEmptyState(reviews: MovieReviews) {
        if (reviews.results.isEmpty()){
            rvReviews.setGone()
            viewEmpty.setVisible()
        }else{
            rvReviews.setVisible()
            viewEmpty.setGone()
        }
    }

    private fun handleStateNetworkError(networkError: Boolean) {
        if (networkError) {
            toast(getString(R.string.message_error_no_internet_body))
        }
    }

    private fun handleStateMovieDetailFromDb(result: List<MovieEntity>) {
        val menuItem = menu?.findItem(R.id.action_favourite)
        menuItem?.let {
            if (result.isEmpty()) {
                favouriteActive = false
                val icon = R.drawable.ic_baseline_favorite_border_white_24
                fabFavourite.setImageResource(icon)
                menuItem.setIcon(icon)
            } else {
                movieEntity = result.first()
                favouriteActive = true
                val icon = R.drawable.ic_baseline_favorite_red_24
                fabFavourite.setImageResource(icon)
                menuItem.setIcon(icon)
            }
        }
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