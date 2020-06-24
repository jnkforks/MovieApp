package com.anggitprayogo.movieapp.feature.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggitprayogo.movieapp.data.remote.entity.Movie
import com.anggitprayogo.movieapp.feature.main.viewholder.MainViewHolder


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