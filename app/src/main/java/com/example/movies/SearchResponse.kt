package com.example.movies

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("Search") val searchResults: List<Movie>
)
