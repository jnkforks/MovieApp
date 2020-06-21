package com.anggitprayogo.core.util.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
fun ImageView.load(imageSrc: Any) {
    Glide.with(context)
        .load(imageSrc)
        .apply(RequestOptions())
        .into(this)
}