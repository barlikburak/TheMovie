package com.nuevo.themovie.service

import com.nuevo.themovie.model.Movie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiSearchQuery {
    @GET("3/search/movie")
    fun getMovies(@Query("api_key") apiKey: String = "c9de574d21823c7fb7752048e26fcd73",
                  @Query("query") query: String) : Single<Movie>
}