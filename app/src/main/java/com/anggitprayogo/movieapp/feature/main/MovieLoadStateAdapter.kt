package com.anggitprayogo.movieapp.feature.main

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.anggitprayogo.movieapp.feature.main.viewholder.MovieLoadStateViewHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Anggit Prayogo on 6/23/20.
 */
@ExperimentalCoroutinesApi
class MovieLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MovieLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: MovieLoadStateViewHolder, loadState: LoadState) {
        holder.bindItem(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieLoadStateViewHolder {
        return MovieLoadStateViewHolder.create(parent, retry)
    }
}