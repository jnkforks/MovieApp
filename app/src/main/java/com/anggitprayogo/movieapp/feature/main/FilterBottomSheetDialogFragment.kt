package com.anggitprayogo.movieapp.feature.main

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.anggitprayogo.movieapp.R
import com.anggitprayogo.movieapp.data.enum.MovieFilter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


/**
 * Created by Anggit Prayogo on 6/21/20.
 */
class FilterBottomSheetDialogFragment : BottomSheetDialogFragment(), View.OnClickListener {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    private var mListener: ItemClickListener? = null

    fun newInstance(): FilterBottomSheetDialogFragment? {
        return FilterBottomSheetDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.filter_bottom_sheet_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.tvPopularMovie).setOnClickListener(this)
        view.findViewById<TextView>(R.id.tvNowPlaying).setOnClickListener(this)
        view.findViewById<TextView>(R.id.tvUpcomingMovie).setOnClickListener(this)
        view.findViewById<TextView>(R.id.tvTopRatedMovie).setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is ItemClickListener) {
            context
        } else {
            throw RuntimeException(
                "$context must implement ItemClickListener"
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onClick(view: View) {
        val movieFilter = getMovieFilter(view.id)
        mListener?.onItemClick(movieFilter)
        dismiss()
    }

    private fun getMovieFilter(id: Int): MovieFilter? {
        return when (id) {
            R.id.tvPopularMovie -> MovieFilter.POPULAR
            R.id.tvUpcomingMovie -> MovieFilter.UP_COMING
            R.id.tvTopRatedMovie -> MovieFilter.TOP_RATED
            R.id.tvNowPlaying -> MovieFilter.NOW_PLAYING
            else -> null
        }
    }

    interface ItemClickListener {
        fun onItemClick(item: MovieFilter?)
    }

    companion object {
        const val TAG = "FilterBottomSheetDialogFragment"
    }
}