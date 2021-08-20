package com.nuevo.themovie.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nuevo.themovie.model.Movie
import com.nuevo.themovie.service.MovieAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MovieViewModel: ViewModel() {

    val movies = MutableLiveData<Movie>()
    val errorMessage = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private val apiService = MovieAPIService()
    private val disposable = CompositeDisposable()

    fun refreshData(search: Boolean, string: String) {
        loading.value = true

        disposable.add(apiService.getData(search, string)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Movie>() {
                override fun onSuccess(t: Movie) {
                    showMovies(t)
                }

                override fun onError(e: Throwable) {
                    println("ERROR: ${e.message}")
                    errorMessage.value = true
                    loading.value = false
                }

            })
        )
    }

    fun showMovies(movie: Movie) {
        movies.value = movie
        errorMessage.value = false
        loading.value = false
    }

}