package com.anggitprayogo.movieapp.feature.favouritelist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggitprayogo.core.util.ext.load
import com.anggitprayogo.movieapp.R
import com.anggitprayogo.movieapp.data.db.entity.MovieEntity
import com.anggitprayogo.movieapp.feature.detail.MovieDetailActivity
import com.anggitprayogo.movieapp.feature.favouritedetail.FavouriteDetailActivity
import kotlinx.android.synthetic.main.row_item_movie.view.*

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
class FavouriteListAdapter : RecyclerView.Adapter<FavouriteListAdapter.ViewHolder>() {

    private var items: MutableList<MovieEntity> = mutableListOf()

    fun setItems(items: MutableList<MovieEntity>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.inflate(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position])
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

        fun bindItem(movie: MovieEntity) {
            with(itemView) {
                ivMovie.load(movie.posterUrl ?: "")
                tvMovieTitle.text = movie.title
                tvReleaseDate.text = movie.releaseDate
                tvOverview.text = movie.overview
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, FavouriteDetailActivity::class.java).apply {
                    putExtra(MovieDetailActivity.MOVIE_ID_KEY, movie.movieId.toString())
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}