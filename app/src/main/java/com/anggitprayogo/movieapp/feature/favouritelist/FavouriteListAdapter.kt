package com.anggitprayogo.movieapp.feature.favouritelist

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
import com.anggitprayogo.movieapp.feature.favouritedetail.FavouriteDetailActivity
import kotlinx.android.synthetic.main.row_item_movie.view.*

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
class FavouriteListAdapter : RecyclerView.Adapter<FavouriteListAdapter.ViewHolder>() {

    private var items: MutableList<MovieEntity> = mutableListOf()
    private lateinit var activity: FavouriteListActivity

    fun setItems(items: MutableList<MovieEntity>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setActivity(activity: FavouriteListActivity) {
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
            movie: MovieEntity,
            activity: FavouriteListActivity
        ) {
            with(itemView) {
                ivMovie.load(movie.posterUrl ?: "")
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

                val intent = Intent(itemView.context, FavouriteDetailActivity::class.java).apply {
                    putExtra(MovieDetailActivity.MOVIE_ID_KEY, movie.movieId.toString())
                }
                itemView.context.startActivity(intent, activityOptionsCompat.toBundle())
            }
        }
    }
}