package com.nuevo.themovie.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("results")
    val movies:List<Movie>?,
    @SerializedName("poster_path")
    val poster_path:String?,
    @SerializedName("adult")
    val adult:Boolean?,
    @SerializedName("overview")
    val overview:String?,
    @SerializedName("release_date")
    val release_date:String?,
    @SerializedName("genre_ids")
    val genre_ids:List<Int>?,
    @SerializedName("id")
    val id:Int?,
    @SerializedName("original_title")
    val original_title:String?,
    @SerializedName("original_language")
    val original_language:String?,
    @SerializedName("title")
    val title:String?,
    @SerializedName("backdrop_path")
    val backdrop_path:String?,
    @SerializedName("popularity")
    val popularity:Number?,
    @SerializedName("vote_count")
    val vote_count:Int?,
    @SerializedName("video")
    val video:Boolean?,
    @SerializedName("vote_average")
    val vote_average:Double?,
    @SerializedName("belongs_to_collection")
    val belongs_to_collection:Any?,
    @SerializedName("budget")
    val budget:Int?,
    @SerializedName("genres")
    val genres:List<Genres>?,
    @SerializedName("homepage")
    val homepage:String?,
    @SerializedName("imdb_id")
    val imdb_id:String?,
    @SerializedName("production_companies")
    val production_companies:List<ProductionCompanies>?,
    @SerializedName("production_countries")
    val production_countries:List<ProductionCountries>?,
    @SerializedName("revenue")
    val revenue:Int?,
    @SerializedName("runtime")
    val runtime:Int?,
    @SerializedName("spoken_languages")
    val spoken_languages:List<SpokenLanguages>?,
    @SerializedName("status")
    val status:String?,
    @SerializedName("tagline")
    val tagline:String?
){
    fun genresToString(): String {
        var string = ""
        genres?.let {
            for(index in it.indices){
                string += it[index].name
                if(index != it.size - 1) {
                    string += ", "
                }
            }
        }
        return string
    }

    fun spokenLanguagesToString(): String {
        var string = ""
        spoken_languages?.let {
            for(index in it.indices){
                string += it[index].name
                if(index != it.size - 1) {
                    string += ", "
                }
            }
        }
        return string
    }

    fun productionCountriesToString(): String {
        var string = ""
        production_countries?.let {
            for(index in it.indices){
                string += it[index].name
                if(index != it.size - 1) {
                    string += ", "
                }
            }
        }
        return string
    }

    fun productionCompaniesToString(): String {
        var string = ""
        production_companies?.let {
            for(index in it.indices){
                string += it[index].toString()
                if(index != it.size - 1) {
                    string += ", "
                }
            }
        }
        return string
    }

}