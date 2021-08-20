package com.nuevo.themovie.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nuevo.themovie.R
import com.nuevo.themovie.util.doPlaceHolder
import com.nuevo.themovie.util.downloadImage
import com.nuevo.themovie.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieDetailsFragment : Fragment() {

    private var movieID = 0

    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            movieID = MovieDetailsFragmentArgs.fromBundle(it).movieId
        }

        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        viewModel.refreshData(false, movieID.toString())

        observeLiveData()
    }

    fun observeLiveData() {
        viewModel.movies.observe(viewLifecycleOwner, Observer{
            title.text = it.title
            if(it.toStringGenres().equals("")) {
                genres_layout.visibility = View.GONE
            } else {
                genres_layout.visibility = View.VISIBLE
                genres.text = it.toStringGenres()
            }
            if(it.overview==null || it.overview.equals("")) {
                overview_layout.visibility = View.GONE
            } else {
                overview_layout.visibility = View.VISIBLE
                overview.text = it.overview
            }
            if(it.homepage==null || it.homepage.equals("")) {
                homepage_layout.visibility = View.GONE
            } else {
                homepage_layout.visibility = View.VISIBLE
                homepage.text = it.homepage
            }
            status.text = it.status
            if(it.adult==null || it.adult) {
                adult.text = "Yes"
            } else {
                adult.text = "No"
            }
            release_date.text = it.release_date
            vote_count.text = it.vote_count.toString()
            vote_average.text = it.vote_average.toString()
            popularity.text = it.popularity.toString()
            revenue.text = it.revenue.toString()
            if(it.runtime==null || it.runtime==0) {
                runtime_layout.visibility = View.GONE
            } else {
                runtime_layout.visibility = View.VISIBLE
                runtime.text = it.runtime.toString()
            }
            original_language.text = it.original_language
            if(it.toStringSpokenLanguages().equals("")) {
                spoken_languages_layout.visibility = View.GONE
            } else {
                spoken_languages_layout.visibility = View.VISIBLE
                spoken_languages.text = it.toStringSpokenLanguages()
            }
            original_title.text = it.original_title
            if(it.tagline==null || it.tagline.equals("")) {
                tagline_layout.visibility = View.GONE
            } else {
                tagline_layout.visibility = View.VISIBLE
                tagline.text = it.tagline
            }
            if(it.toStringProductionCountries().equals("")) {
                production_countries_layout.visibility = View.GONE
            } else {
                production_countries_layout.visibility = View.VISIBLE
                production_countries.text = it.toStringProductionCountries()
            }
            if(it.toStringProductionCompanies().equals("")) {
                production_companies_layout.visibility = View.GONE
            } else {
                production_companies_layout.visibility = View.VISIBLE
                production_companies.text = it.toStringProductionCompanies()
            }
            image_movie.downloadImage("https://image.tmdb.org/t/p/w500" + it.backdrop_path, doPlaceHolder(requireContext()))
            linearLayout.visibility = View.VISIBLE
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if(it) {
                    movie_error_message.visibility = View.VISIBLE
                    linearLayout.visibility = View.GONE
                } else {
                    movie_error_message.visibility = View.GONE
                }
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { movieLoad ->
            movieLoad?.let {
                if(it) {
                    movie_error_message.visibility = View.GONE
                    linearLayout.visibility = View.GONE
                    movie_loading.visibility = View.VISIBLE
                } else {
                    movie_loading.visibility = View.GONE
                }
            }
        })
    }

}