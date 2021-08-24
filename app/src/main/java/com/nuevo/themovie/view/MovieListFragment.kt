package com.nuevo.themovie.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.nuevo.themovie.R
import com.nuevo.themovie.adapter.MovieAdapter
import com.nuevo.themovie.databinding.FragmentMovieListBinding
import com.nuevo.themovie.model.Movie
import com.nuevo.themovie.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        viewModel.loadListOfData(false, "top_rated")

        listenToAllViews()
        observeLiveData()
    }

    private fun listenToAllViews() {
        binding.typeOfList.setOnClickListener {
            if(binding.typeOfList.text.toString() == "POPULARITY") {
                binding.typeOfList.setText(R.string.topRated)
                viewModel.loadListOfData(false, "top_rated")
            } else {
                binding.typeOfList.setText(R.string.popular)
                viewModel.loadListOfData(false, "popular")
            }
        }

        binding.gridView.setOnScrollListener(object: AbsListView.OnScrollListener{
            override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {}

            override fun onScroll(p0: AbsListView?, p1: Int, p2: Int, p3: Int) {
                if (binding.gridView.getChildAt(0) != null)
                    binding.swipeRefreshLayout.isEnabled = binding.gridView.firstVisiblePosition == 0
            }

        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.loading.visibility = View.VISIBLE
            binding.errorMessage.visibility = View.GONE
            binding.gridView.visibility = View.GONE
            if(binding.editSearch.query.length < 3)
                viewModel.loadListOfData(false, if(binding.typeOfList.text.toString() == "POPULARITY")   "popular"  else    "top_rated")
            else
                viewModel.loadListOfData(true, binding.editSearch.query.toString())
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.editSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if(it.length > 2)
                        viewModel.loadListOfData(true, it)
                }
                return false
            }

        })
    }

    private fun observeLiveData() {
        viewModel.getMovie().observe(viewLifecycleOwner, { movies ->
            movies?.let {
                binding.gridView.visibility = View.VISIBLE
                binding.gridView.adapter = MovieAdapter(requireContext(), movies.movies as ArrayList<Movie>, binding.typeOfList.text.toString())
            }
        })

        viewModel.getErrorMessage().observe(viewLifecycleOwner, { error ->
            error?.let {
                if(it) {
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.gridView.visibility = View.GONE
                } else
                    binding.errorMessage.visibility = View.GONE
            }
        })

        viewModel.getLoading().observe(viewLifecycleOwner, { movieLoad ->
            movieLoad?.let {
                if(it) {
                    binding.errorMessage.visibility = View.GONE
                    binding.gridView.visibility = View.GONE
                    binding.loading.visibility = View.VISIBLE
                } else
                    binding.loading.visibility = View.GONE
            }
        })
    }

}