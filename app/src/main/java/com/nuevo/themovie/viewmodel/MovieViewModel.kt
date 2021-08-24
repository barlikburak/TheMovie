package com.nuevo.themovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nuevo.themovie.model.Movie
import com.nuevo.themovie.service.MovieAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieAPI: MovieAPI) : ViewModel(){

    private val movie = MutableLiveData<Movie>()
    private val errorMessage = MutableLiveData<Boolean>()
    private val loading = MutableLiveData<Boolean>()

    fun getMovie(): MutableLiveData<Movie> = movie

    fun getErrorMessage(): MutableLiveData<Boolean> = errorMessage

    fun getLoading(): MutableLiveData<Boolean> = loading

    fun loadListOfData(search: Boolean, string: String) {
        loading.postValue(true)
        val call: Call<Movie> =
            if(search)  movieAPI.getMovieSearchQuery(query = string)
            else        movieAPI.getMovieTypeOfList(path = string)

        runBlocking {
            CoroutineScope(Dispatchers.IO).launch {
                call.enqueue(object : Callback<Movie> {
                    override fun onFailure(call: Call<Movie>, t: Throwable) =
                        showMovies(movieValue = null, errorMessageValue = true)

                    override fun onResponse(call: Call<Movie>, response: Response<Movie>) =
                        showMovies(movieValue = response.body(), errorMessageValue = false)

                    private fun showMovies(movieValue: Movie?, errorMessageValue: Boolean, loadingValue: Boolean = false) {
                        movie.postValue(movieValue)
                        errorMessage.postValue(errorMessageValue)
                        loading.postValue(loadingValue)
                    }
                })
            }.join()
        }
    }

}