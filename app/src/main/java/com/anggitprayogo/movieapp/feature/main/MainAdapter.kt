package com.anggitprayogo.movieapp.feature.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.anggitprayogo.core.util.ext.load
import com.anggitprayogo.movieapp.R
import com.anggitprayogo.movieapp.data.remote.entity.Movie
import com.anggitprayogo.movieapp.feature.detail.MovieDetailActivity
import kotlinx.android.synthetic.main.row_item_movie.view.*


/**
 * Created by Anggit Prayogo on 6/21/20.
 */
class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var items: MutableList<Movie> = mutableListOf()
    private lateinit var activity: MainActivity

    fun setItems(items: MutableList<Movie>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setActivity(activity: MainActivity) {
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.inflate(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], activity)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            fun inflate(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.row_item_movie, parent, false)
                )
            }
        }

        fun bindItem(
            movie: Movie,
            activity: MainActivity
        ) {
            with(itemView) {
                ivMovie.load(movie.getPoster())
                tvMovieTitle.text = movie.title
                tvReleaseDate.text = movie.releaseDate
                tvOverview.text = movie.overview
            }

            itemView.setOnClickListener {
                val activityOptionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        itemView.ivMovie,
                        "imageMain"
                    )
                val intent = Intent(itemView.context, MovieDetailActivity::class.java).apply {
                    putExtra(MovieDetailActivity.MOVIE_ID_KEY, movie.id.toString())
                }
                itemView.context.startActivity(intent, activityOptionsCompat.toBundle())
            }
        }
    }
}