package com.nuevo.themovie.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nuevo.themovie.R

private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"

fun ImageView.downloadImage(url: String?, placeholder: CircularProgressDrawable) {
    val options = RequestOptions().placeholder(placeholder).error(R.drawable.movie)
    Glide.with(context).setDefaultRequestOptions(options).load(IMAGE_URL+ url).into(this)
}

fun doPlaceHolder(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}