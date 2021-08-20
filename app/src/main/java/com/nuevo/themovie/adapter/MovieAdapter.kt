package com.nuevo.themovie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.navigation.Navigation
import com.nuevo.themovie.R
import com.nuevo.themovie.model.Movie
import com.nuevo.themovie.util.doPlaceHolder
import com.nuevo.themovie.util.downloadImage
import com.nuevo.themovie.view.MovieListFragmentDirections
import kotlinx.android.synthetic.main.row_item.view.*

class MovieAdapter(val context: Context, val movieList: ArrayList<Movie>, val typeOfListing: String) : BaseAdapter() {

    override fun getCount(): Int {
        return movieList.size
    }

    override fun getItem(p0: Int): Any {
        return movieList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? {
        val movie = this.movieList.get(p0)

        var inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var movieView = inflator.inflate(R.layout.row_item, null)

        movieView.movie_image.downloadImage("https://image.tmdb.org/t/p/w500" + movie.backdrop_path, doPlaceHolder(context))
        movieView.movie_title.setText(movie.title)

        movieView.movie_release_date.setText(movie.release_date)

        if(typeOfListing.equals("top_rated")) {
            movieView.movie_type_of_listing.setText(movie.vote_average.toString())
        } else {
            movieView.movie_type_of_listing.setText(movie.popularity.toString())
        }

        movieView.setOnClickListener {
            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(movie.id!!)
            Navigation.findNavController(it).navigate(action)
        }

        return movieView;
    }

}