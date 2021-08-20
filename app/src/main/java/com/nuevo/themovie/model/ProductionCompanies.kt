package com.nuevo.themovie.model

import com.google.gson.annotations.SerializedName

data class ProductionCompanies(
    @SerializedName("name")
    val name:String?,
    @SerializedName("id")
    val id:Int?,
    @SerializedName("logo_path")
    val logo_path:String?,
    @SerializedName("origin_country")
    val origin_country:String?
) {
    override fun toString(): String {
        return "[name=$name, origin country=$origin_country]"
    }
}
