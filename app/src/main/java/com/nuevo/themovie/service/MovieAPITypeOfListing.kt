package com.nuevo.themovie.service

import com.nuevo.themovie.model.Movie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPITypeOfListing {
    @GET("3/movie/{path}")
    fun getMovies(@Path("path") path: String,
                  @Query("api_key") apiKey: String = "c9de574d21823c7fb7752048e26fcd73") : Single<Movie>
}