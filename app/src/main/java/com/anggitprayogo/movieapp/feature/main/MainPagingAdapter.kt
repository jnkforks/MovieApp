package com.anggitprayogo.movieapp.feature.main

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anggitprayogo.movieapp.data.local.entity.MovieEntity
import com.anggitprayogo.movieapp.feature.main.viewholder.MainViewHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Anggit Prayogo on 6/23/20.
 */
@ExperimentalCoroutinesApi
class MainPagingAdapter: PagingDataAdapter<MovieEntity, RecyclerView.ViewHolder>(MOVIE_COMPARATOR){

    private lateinit var activity: MainActivity

    fun setActivity(activity: MainActivity){
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainViewHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as MainViewHolder).bindItem(repoItem, activity)
        }
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
                oldItem == newItem
        }
    }
}