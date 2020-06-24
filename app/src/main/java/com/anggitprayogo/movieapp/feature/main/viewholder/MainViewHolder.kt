package com.anggitprayogo.movieapp.feature.main.viewholder

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.anggitprayogo.core.util.ext.load
import com.anggitprayogo.movieapp.R
import com.anggitprayogo.movieapp.data.local.entity.MovieEntity
import com.anggitprayogo.movieapp.feature.detail.MovieDetailActivity
import com.anggitprayogo.movieapp.feature.main.MainActivity
import kotlinx.android.synthetic.main.row_item_movie.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Anggit Prayogo on 6/23/20.
 */
@ExperimentalCoroutinesApi
class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    companion object {
        fun inflate(parent: ViewGroup): MainViewHolder {
            return MainViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_item_movie, parent, false)
            )
        }
    }

    fun bindItem(
        movie: MovieEntity?,
        activity: MainActivity?
    ) {
        with(itemView) {
            ivMovie.load(movie?.getPoster() ?: "")
            tvMovieTitle.text = movie?.title
            tvReleaseDate.text = movie?.releaseDate
            tvOverview.text = movie?.overview
        }

        itemView.setOnClickListener {
            val activityOptionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity!!,
                    itemView.ivMovie,
                    "imageMain"
                )
            val intent = Intent(itemView.context, MovieDetailActivity::class.java).apply {
                putExtra(MovieDetailActivity.MOVIE_ID_KEY, movie?.movieId.toString())
            }
            itemView.context.startActivity(intent, activityOptionsCompat.toBundle())
        }
    }
}