package com.nuevo.themovie.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import com.nuevo.themovie.R
import com.nuevo.themovie.adapter.MovieAdapter
import com.nuevo.themovie.databinding.FragmentMovieListBinding
import com.nuevo.themovie.model.Movie
import com.nuevo.themovie.viewmodel.MovieViewModel

class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding

    private lateinit var viewModel: MovieViewModel
    private var editSearchQuery: String = ""
    private var typeOfList: String = "top_rated"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        viewModel.refreshData(false, typeOfList)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.loading.visibility = View.VISIBLE
            binding.errorMessage.visibility = View.GONE
            binding.gridView.visibility = View.GONE
            if(editSearchQuery == "") {
                viewModel.refreshData(false, typeOfList)
            } else {
                viewModel.refreshData(true, editSearchQuery)
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.editSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(editSearchQuery.length > 2) {
                    viewModel.refreshData(true, editSearchQuery)
                } else {
                    editSearchQuery = ""
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if(it.length > 2) {
                        editSearchQuery = it
                        viewModel.refreshData(true, it)
                    } else {
                        editSearchQuery = ""
                    }
                }
                return false
            }

        })

        binding.typeOfList.setOnClickListener {
            typeOfList = if(typeOfList == "top_rated") {
                binding.typeOfList.setText(R.string.popular)
                "popular"
            } else {
                binding.typeOfList.setText(R.string.topRated)
                "top_rated"
            }
            viewModel.refreshData(false, typeOfList)
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.movies.observe(viewLifecycleOwner, { movies ->
            movies?.let {
                binding.gridView.visibility = View.VISIBLE
                binding.gridView.adapter = MovieAdapter(requireContext(), movies.movies as ArrayList<Movie>, typeOfList)
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, { error ->
            error?.let {
                if(it) {
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.gridView.visibility = View.GONE
                } else {
                    binding.errorMessage.visibility = View.GONE
                }
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, { movieLoad ->
            movieLoad?.let {
                if(it) {
                    binding.errorMessage.visibility = View.GONE
                    binding.gridView.visibility = View.GONE
                    binding.loading.visibility = View.VISIBLE
                } else {
                    binding.loading.visibility = View.GONE
                }
            }
        })
    }

}