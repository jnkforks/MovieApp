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
import com.anggitprayogo.movieapp.feature.main.viewholder.MainViewHolder
import kotlinx.android.synthetic.main.row_item_movie.view.*


/**
 * Created by Anggit Prayogo on 6/21/20.
 */
class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {

    private var items: MutableList<Movie> = mutableListOf()
    private lateinit var activity: MainActivity

    fun setItems(items: MutableList<Movie>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setActivity(activity: MainActivity) {
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder.inflate(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindItem(items[position], activity)
    }
}