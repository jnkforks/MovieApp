package com.anggitprayogo.movieapp.feature.favouritedetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.anggitprayogo.core.base.BaseActivity
import com.anggitprayogo.core.util.ext.load
import com.anggitprayogo.core.util.ext.toast
import com.anggitprayogo.movieapp.BaseApplication
import com.anggitprayogo.movieapp.R
import com.anggitprayogo.movieapp.data.local.entity.FavouriteEntity
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_favourite_detail.appBarLayout
import kotlinx.android.synthetic.main.activity_favourite_detail.fabFavourite
import kotlinx.android.synthetic.main.activity_favourite_detail.ivBannerMovie
import kotlinx.android.synthetic.main.activity_favourite_detail.ratingMovie
import kotlinx.android.synthetic.main.activity_favourite_detail.toolbar
import kotlinx.android.synthetic.main.activity_favourite_detail.tvImdbRating
import kotlinx.android.synthetic.main.activity_favourite_detail.tvMovieMetaData
import kotlinx.android.synthetic.main.activity_favourite_detail.tvMovieTitle
import kotlinx.android.synthetic.main.activity_favourite_detail.tvOverview
import javax.inject.Inject

class FavouriteDetailActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FavouriteDetailViewModel

    private var movieId: String? = null
    private var favouriteEntity: FavouriteEntity? = null

    private var menu: Menu? = null
    private var favouriteActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_detail)
        handleIntent()
        initViewModel()
        fetchData()
        initObserver()
        initToolbar()
        initListner()
    }

    private fun initListner() {
        fabFavourite.setOnClickListener {
            addOrRemoveMovieAction()
        }
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
        setActionBarTitle(favouriteEntity?.title)
    }

    private fun setActionBarTitle(title: String?) {
        supportActionBar?.title = title
    }

    private fun initObserver() {
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

        viewModel.error.observe(this, Observer {
            toast(it)
        })
    }

    private fun fetchData() {
        movieId?.let {
            viewModel.getMovieDetailDb(it)
        }
    }

    private fun handleIntent() {
        movieId = intent.getStringExtra(MOVIE_ID_KEY)
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)[FavouriteDetailViewModel::class.java]
    }

    private fun addOrRemoveMovieAction() {
        if (favouriteActive) {
            favouriteEntity?.let { it1 -> viewModel.deleteMovieFromDb(it1) }
        } else {
            favouriteEntity?.let { it1 -> viewModel.insertMovieToDb(it1) }
        }
    }

    private fun handleStateMovieDetailFromDb(result: List<FavouriteEntity>) {
        val menuItem = menu?.findItem(R.id.action_favourite)
        menuItem?.let {
            if (result.isEmpty()) {
                favouriteActive = false
                val icon = R.drawable.ic_baseline_favorite_border_white_24
                fabFavourite.setImageResource(icon)
                handleBugFloatActionButton()
                menuItem.setIcon(icon)
            } else {
                favouriteEntity = result.first()
                favouriteActive = true
                val icon = R.drawable.ic_baseline_favorite_red_24
                fabFavourite.setImageResource(icon)
                handleBugFloatActionButton()
                menuItem.setIcon(icon)
                bindDataToView()
            }
        }
    }

    private fun bindDataToView() {
        favouriteEntity?.let { movie ->
            ivBannerMovie.load(movie.bannerUrl ?: "")
            tvMovieMetaData.text = movie.genres
            tvMovieTitle.text = movie.title
            tvImdbRating.text = getString(
                R.string.imdb_rating_template,
                (movie.vote?.toFloat() ?: 0.0).toString()
            )
            ratingMovie.rating = movie.vote?.toFloat() ?: 0f
            tvOverview.text = movie.overview
        }
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
            addOrRemoveMovieAction()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun handleBugFloatActionButton(){
        fabFavourite.hide();
        fabFavourite.show();
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