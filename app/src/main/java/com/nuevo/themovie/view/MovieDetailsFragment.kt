package com.nuevo.themovie.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.nuevo.themovie.R
import com.nuevo.themovie.databinding.FragmentMovieDetailsBinding
import com.nuevo.themovie.util.doPlaceHolder
import com.nuevo.themovie.util.downloadImage
import com.nuevo.themovie.viewmodel.MovieViewModel

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

        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        viewModel.refreshData(false, movieID.toString())

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.movies.observe(viewLifecycleOwner, {
            binding.title.text = it.title
            if(it.toStringGenres() == "") {
                binding.genresLayout.visibility = View.GONE
            } else {
                binding.genresLayout.visibility = View.VISIBLE
                binding.genres.text = it.toStringGenres()
            }
            if(it.overview==null || it.overview == "") {
                binding.overviewLayout.visibility = View.GONE
            } else {
                binding.overviewLayout.visibility = View.VISIBLE
                binding.overview.text = it.overview
            }
            if(it.homepage==null || it.homepage == "") {
                binding.homepageLayout.visibility = View.GONE
            } else {
                binding.homepageLayout.visibility = View.VISIBLE
                binding.homepage.text = it.homepage
            }
            binding.status.text = it.status
            if(it.adult==null || it.adult) {
                binding.adult.setText(R.string.y)
            } else {
                binding.adult.setText(R.string.n)
            }
            binding.releaseDate.text = it.release_date
            binding.voteCount.text = it.vote_count.toString()
            binding.voteAverage.text = it.vote_average.toString()
            binding.popularity.text = it.popularity.toString()
            binding.revenue.text = it.revenue.toString()
            if(it.runtime==null || it.runtime==0) {
                binding.runtimeLayout.visibility = View.GONE
            } else {
                binding.runtimeLayout.visibility = View.VISIBLE
                binding.runtime.text = it.runtime.toString()
            }
            binding.originalLanguage.text = it.original_language
            if(it.toStringSpokenLanguages() == "") {
                binding.spokenLanguagesLayout.visibility = View.GONE
            } else {
                binding.spokenLanguagesLayout.visibility = View.VISIBLE
                binding.spokenLanguages.text = it.toStringSpokenLanguages()
            }
            binding.originalTitle.text = it.original_title
            if(it.tagline==null || it.tagline == "") {
                binding.taglineLayout.visibility = View.GONE
            } else {
                binding.taglineLayout.visibility = View.VISIBLE
                binding.tagline.text = it.tagline
            }
            if(it.toStringProductionCountries() == "") {
                binding.productionCountriesLayout.visibility = View.GONE
            } else {
                binding.productionCountriesLayout.visibility = View.VISIBLE
                binding.productionCountries.text = it.toStringProductionCountries()
            }
            if(it.toStringProductionCompanies() == "") {
                binding.productionCompaniesLayout.visibility = View.GONE
            } else {
                binding.productionCompaniesLayout.visibility = View.VISIBLE
                binding.productionCompanies.text = it.toStringProductionCompanies()
            }
            binding.imageMovie.downloadImage("https://image.tmdb.org/t/p/w500" + it.backdrop_path, doPlaceHolder(requireContext()))
            binding.linearLayout.visibility = View.VISIBLE
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, { error ->
            error?.let {
                if(it) {
                    binding.movieErrorMessage.visibility = View.VISIBLE
                    binding.movieLoading.visibility = View.GONE
                } else {
                    binding.movieErrorMessage.visibility = View.GONE
                }
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, { movieLoad ->
            movieLoad?.let {
                if(it) {
                    binding.movieErrorMessage.visibility = View.GONE
                    binding.linearLayout.visibility = View.GONE
                    binding.movieLoading.visibility = View.VISIBLE
                } else {
                    binding.movieLoading.visibility = View.GONE
                }
            }
        })
    }

}