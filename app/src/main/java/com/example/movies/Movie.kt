package com.example.movies

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Title") val title: String?,
    @SerializedName("Year") val year: String?,
    @SerializedName("Poster") val posterUrl: String?,
    @SerializedName("Director") val director: String?,
    @SerializedName("Genre") val genre: String?,
    @SerializedName("Country") val country: String?,
    @SerializedName("imdbRating") val rating: String?,
    @SerializedName("Plot") val plot: String?,
    @SerializedName("Actors") val actors: String?,
    @SerializedName("Awards") val awards: String?
) {
    companion object {
        fun fromJson(json: String): Movie {
            return Gson().fromJson(json, Movie::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}




