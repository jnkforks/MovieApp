package com.anggitprayogo.movieapp.feature.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggitprayogo.movieapp.R
import com.anggitprayogo.movieapp.data.entity.Review
import kotlinx.android.synthetic.main.row_item_review.view.*

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
class ReviewsAdapter: RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    private var reviewsList = mutableListOf<Review>()

    fun setItems(reviewList: MutableList<Review>){
        this.reviewsList = reviewList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.inflate(parent)
    }

    override fun getItemCount(): Int = reviewsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(reviewsList[position])
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        companion object {
            fun inflate(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.row_item_review, parent, false)
                )
            }
        }

        fun bindView(review: Review) {
            with(itemView){
                tvAuthor.text = review.author
                tvContent.text = review.content
            }
        }
    }
}