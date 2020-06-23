package com.anggitprayogo.movieapp.feature.main.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.anggitprayogo.movieapp.R
import kotlinx.android.synthetic.main.base_loading_footer_movie.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Anggit Prayogo on 6/23/20.
 */
@ExperimentalCoroutinesApi
class MovieLoadStateViewHolder(
    view: View,
    retry: () -> Unit
) : RecyclerView.ViewHolder(view) {

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): MovieLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.base_loading_footer_movie, parent, false)
            return MovieLoadStateViewHolder(view, retry)
        }
    }

    init {
        view.findViewById<Button>(R.id.retryButton).setOnClickListener {
            retry.invoke()
        }
    }

    fun bindItem(
        loadState: LoadState
    ) {
        with(itemView) {
            if (loadState is LoadState.Error) {
                errorMsg.text = loadState.error.localizedMessage
            }
            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState !is LoadState.Loading
            errorMsg.isVisible = loadState !is LoadState.Loading
        }
    }
}