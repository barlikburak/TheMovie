package com.nuevo.themovie.service

import com.nuevo.themovie.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    @GET("3/search/movie")
    fun getMovieSearchQuery(@Query("api_key") api_key: String = "c9de574d21823c7fb7752048e26fcd73",
                  @Query("query") query: String) : Call<Movie>

    @GET("3/movie/{path}")
    fun getMovieTypeOfList(@Path("path") path: String,
                  @Query("api_key") apiKey: String = "c9de574d21823c7fb7752048e26fcd73") : Call<Movie>


}