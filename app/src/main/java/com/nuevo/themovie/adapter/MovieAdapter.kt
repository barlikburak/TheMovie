package com.nuevo.themovie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.navigation.Navigation
import com.nuevo.themovie.databinding.RowItemBinding
import com.nuevo.themovie.model.Movie
import com.nuevo.themovie.util.doPlaceHolder
import com.nuevo.themovie.util.downloadImage
import com.nuevo.themovie.view.MovieListFragmentDirections

class MovieAdapter(private val context: Context, private val movieList: ArrayList<Movie>, private val typeOfList: String) : BaseAdapter() {

    private lateinit var binding: RowItemBinding

    override fun getCount(): Int {
        return movieList.size
    }

    override fun getItem(p0: Int): Any {
        return movieList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val movie = this.movieList[p0]

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RowItemBinding.inflate(inflater)
        val movieView =  binding.root

        assignData(movie)

        movieView.setOnClickListener {
            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(movie.id!!)
            Navigation.findNavController(it).navigate(action)
        }

        return movieView
    }

    private fun assignData(movie: Movie) {
        binding.movieImage.downloadImage(movie.backdrop_path, doPlaceHolder(context))

        binding.movieTitle.text = movie.title
        binding.movieReleaseDate.text = movie.release_date
        binding.movieTypeOfListing.text =
            if(typeOfList != "POPULARITY") movie.vote_average.toString()
            else movie.popularity.toString()
    }

}