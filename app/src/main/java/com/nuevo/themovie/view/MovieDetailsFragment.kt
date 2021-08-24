package com.nuevo.themovie.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.nuevo.themovie.databinding.FragmentMovieDetailsBinding
import com.nuevo.themovie.model.Movie
import com.nuevo.themovie.util.doPlaceHolder
import com.nuevo.themovie.util.downloadImage
import com.nuevo.themovie.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var movieID = 0
    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            movieID = MovieDetailsFragmentArgs.fromBundle(it).movieId
        }

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        viewModel.loadListOfData(false, movieID.toString())

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.getMovie().observe(viewLifecycleOwner, {
            assignData(it)
            binding.linearLayout.visibility = View.VISIBLE
        })

        viewModel.getErrorMessage().observe(viewLifecycleOwner, { error ->
            error?.let {
                if(it) {
                    binding.movieErrorMessage.visibility = View.VISIBLE
                    binding.movieLoading.visibility = View.GONE
                } else
                    binding.movieErrorMessage.visibility = View.GONE
            }
        })

        viewModel.getLoading().observe(viewLifecycleOwner, { movieLoad ->
            movieLoad?.let {
                if(it) {
                    binding.movieErrorMessage.visibility = View.GONE
                    binding.linearLayout.visibility = View.GONE
                    binding.movieLoading.visibility = View.VISIBLE
                } else
                    binding.movieLoading.visibility = View.GONE
            }
        })
    }

    private fun assignData(it: Movie) {
        viewVisibility(layout = null, data = it.title, textView = binding.title)
        viewVisibility(layout = binding.genresLayout, data = it.genresToString(), textView = binding.genres)
        viewVisibility(layout = binding.overviewLayout, data = it.overview, textView = binding.overview)
        viewVisibility(layout = binding.homepageLayout, data = it.homepage, textView = binding.homepage)
        viewVisibility(layout = null, data = it.status, textView =binding.status)
        viewVisibility(layout = null, data = if(it.adult!=null && it.adult) "Yes" else "No", textView = binding.adult)
        viewVisibility(layout = null, data = it.release_date, textView = binding.releaseDate)
        viewVisibility(layout = null, data = it.vote_count, textView = binding.voteCount)
        viewVisibility(layout = null, data = it.vote_average, textView = binding.voteAverage)
        viewVisibility(layout = null, data = it.popularity, textView = binding.popularity)
        viewVisibility(layout = null, data = it.revenue, textView = binding.revenue)
        viewVisibility(layout = binding.runtimeLayout, data = it.runtime, textView = binding.runtime)
        viewVisibility(layout = null, data = it.original_language, textView = binding.originalLanguage)
        viewVisibility(layout = binding.spokenLanguagesLayout, data = it.spokenLanguagesToString(), textView = binding.spokenLanguages)
        viewVisibility(layout = null, data = it.original_title, textView = binding.originalTitle)
        viewVisibility(layout = binding.taglineLayout, data = it.tagline, textView = binding.tagline)
        viewVisibility(layout = binding.productionCountriesLayout, data = it.productionCountriesToString(), textView = binding.productionCountries)
        viewVisibility(layout = binding.productionCompaniesLayout, data = it.productionCompaniesToString(), textView = binding.productionCompanies)
        viewVisibility(layout = null, data = it.backdrop_path, imageView = binding.imageMovie)
    }

    private fun viewVisibility(layout: View?, data: Any?, textView: TextView? = null, imageView: ImageView? = null) {
        if(textView != null) {
            if (data == null || data.toString() == "" || data.toString() == "0")   layout?.visibility = View.GONE
            else {
                layout?.visibility = View.VISIBLE
                textView.text = data.toString()
            }
        } else imageView?.downloadImage(data.toString(), doPlaceHolder(requireContext()))
    }

}