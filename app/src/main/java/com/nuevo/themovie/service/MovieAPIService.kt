package com.nuevo.themovie.service

import com.nuevo.themovie.model.Movie
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieAPIService {

    private val BASE_URL = "https://api.themoviedb.org/"

    fun getData(search: Boolean, string: String) : Single<Movie> {
        return if(search) {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MovieApiSearchQuery::class.java).getMovies(query = string)
        } else {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MovieAPITypeOfListing::class.java).getMovies(path = string)
        }
    }

}