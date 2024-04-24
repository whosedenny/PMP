package com.example.movies

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbService {
    @GET("/")
    fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") movieTitle: String
    ): Call<SearchResponse>

    @GET("/")
    fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("t") movieTitle: String
    ): Call<MovieDetailResponse>
}



