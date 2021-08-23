package com.nuevo.themovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nuevo.themovie.model.Movie
import com.nuevo.themovie.service.RetrofitMovieAPICall
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val retroApiCall: RetrofitMovieAPICall) : ViewModel() {

    private val movie = MutableLiveData<Movie>()
    private val movieErrorMessage = MutableLiveData<Boolean>()
    private val movieLoading = MutableLiveData<Boolean>()

    fun getMovie(): MutableLiveData<Movie> = movie

    fun getMovieErrorMessage(): MutableLiveData<Boolean> = movieErrorMessage

    fun getMovieLoading(): MutableLiveData<Boolean> = movieLoading


    fun loadListOfData(string: String) {
        retroApiCall.makeApiCall(string, movie, mo)
    }

}