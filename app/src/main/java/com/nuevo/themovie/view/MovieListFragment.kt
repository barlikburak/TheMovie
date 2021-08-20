package com.nuevo.themovie.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nuevo.themovie.R
import com.nuevo.themovie.adapter.MovieAdapter
import com.nuevo.themovie.model.Movie
import com.nuevo.themovie.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.row_item.*

class MovieListFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel
    private var editSearchQuery: String = ""
    private var typeOfListing: String = "top_rated"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        viewModel.refreshData(false, typeOfListing)

        swipe_refresh_layout.setOnRefreshListener {
            loading.visibility = View.VISIBLE
            error_message.visibility = View.GONE
            grid_view.visibility = View.GONE
            if(editSearchQuery.equals("")) {
                viewModel.refreshData(false, typeOfListing)
            } else {
                viewModel.refreshData(true, editSearchQuery)
            }
            swipe_refresh_layout.isRefreshing = false
        }

        edit_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

        type_of_list.setOnClickListener {
            if(typeOfListing.equals("top_rated")) {
                movie_type_of_listing.text = "POPULARITY"
                typeOfListing = "popular"
            } else {
                movie_type_of_listing.text = "TOP RATED"
                typeOfListing = "top_rated"
            }
            viewModel.refreshData(false, typeOfListing)
        }

        observeLiveData()
    }

    fun observeLiveData() {
        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            movies?.let {
                grid_view.visibility = View.VISIBLE
                grid_view.adapter = MovieAdapter(requireContext(), movies.movies as ArrayList<Movie>, typeOfListing)
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if(it) {
                    error_message.visibility = View.VISIBLE
                    grid_view.visibility = View.GONE
                } else {
                    error_message.visibility = View.GONE
                }
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { movieLoad ->
            movieLoad?.let {
                if(it) {
                    error_message.visibility = View.GONE
                    grid_view.visibility = View.GONE
                    loading.visibility = View.VISIBLE
                } else {
                    loading.visibility = View.GONE
                }
            }
        })
    }


}