package com.anggitprayogo.movieapp.feature.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggitprayogo.core.util.ext.load
import com.anggitprayogo.movieapp.R
import com.anggitprayogo.movieapp.data.entity.Movie
import kotlinx.android.synthetic.main.row_item_movie.view.*

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
class MainAdapter() : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var items: MutableList<Movie> = mutableListOf()

    fun setItems(items: MutableList<Movie>) {
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

        fun bindItem(movie: Movie) {
            with(itemView) {
                ivMovie.load(movie.getPoster())
                tvMovieTitle.text = movie.title
                tvReleaseDate.text = movie.releaseDate
                tvOverview.text = movie.overview
            }
        }
    }
}