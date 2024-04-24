package com.example.movies

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val yearTextView = findViewById<TextView>(R.id.yearTextView)
        val directorTextView = findViewById<TextView>(R.id.directorTextView)
        val genreTextView = findViewById<TextView>(R.id.genreTextView)
        val countryTextView = findViewById<TextView>(R.id.countryTextView)
        val ratingTextView = findViewById<TextView>(R.id.ratingTextView)
        val posterImageView = findViewById<ImageView>(R.id.posterImageView)
        val releasedTextView = findViewById<TextView>(R.id.releasedTextView)
        val runtimeTextView = findViewById<TextView>(R.id.runtimeTextView)
        val writerTextView = findViewById<TextView>(R.id.writerTextView)
        val actorsTextView = findViewById<TextView>(R.id.actorsTextView)
        val plotTextView = findViewById<TextView>(R.id.plotTextView)
        val languageTextView = findViewById<TextView>(R.id.languageTextView)
        val awardsTextView = findViewById<TextView>(R.id.awardsTextView)

        val jsonMovie = intent.getStringExtra("movie")
        val movie = Gson().fromJson(jsonMovie, Movie::class.java)

        titleTextView.text = "Title: ${movie.title ?: "N/A"}"
        yearTextView.text = "Year: ${movie.year ?: "N/A"}"

        val omdbService = OMDbApiClient.getClient().create(OMDbService::class.java)
        val call = omdbService.getMovieDetails("5a85c2c2", movie.title ?: "")
        call.enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>) {
                if (response.isSuccessful) {
                    val movieDetailResponse = response.body()
                    if (movieDetailResponse != null) {
                        directorTextView.text = "Director: ${movieDetailResponse.director ?: "N/A"}"
                        genreTextView.text = "Genre: ${movieDetailResponse.genre ?: "N/A"}"
                        countryTextView.text = "Country: ${movieDetailResponse.country ?: "N/A"}"
                        ratingTextView.text = "Rating: ${movieDetailResponse.rating ?: "N/A"}"
                        releasedTextView.text = "Released: ${movieDetailResponse.released ?: "N/A"}"
                        runtimeTextView.text = "Runtime: ${movieDetailResponse.runtime ?: "N/A"}"
                        writerTextView.text = "Writer: ${movieDetailResponse.writer ?: "N/A"}"
                        actorsTextView.text = "Actors: ${movieDetailResponse.actors ?: "N/A"}"
                        plotTextView.text = "Plot: ${movieDetailResponse.plot ?: "N/A"}"
                        languageTextView.text = "Language: ${movieDetailResponse.language ?: "N/A"}"
                        awardsTextView.text = "Awards: ${movieDetailResponse.awards ?: "N/A"}"
                    } else {
                        directorTextView.text = "Director: N/A"
                        genreTextView.text = "Genre: N/A"
                        countryTextView.text = "Country: N/A"
                        ratingTextView.text = "Rating: N/A"
                        releasedTextView.text = "Released: N/A"
                        runtimeTextView.text = "Runtime: N/A"
                        writerTextView.text = "Writer: N/A"
                        actorsTextView.text = "Actors: N/A"
                        plotTextView.text = "Plot: N/A"
                        languageTextView.text = "Language: N/A"
                        awardsTextView.text = "Awards: N/A"
                    }
                } else {
                    directorTextView.text = "Director: N/A"
                    genreTextView.text = "Genre: N/A"
                    countryTextView.text = "Country: N/A"
                    ratingTextView.text = "Rating: N/A"
                    releasedTextView.text = "Released: N/A"
                    runtimeTextView.text = "Runtime: N/A"
                    writerTextView.text = "Writer: N/A"
                    actorsTextView.text = "Actors: N/A"
                    plotTextView.text = "Plot: N/A"
                    languageTextView.text = "Language: N/A"
                    awardsTextView.text = "Awards: N/A"
                }
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                directorTextView.text = "Director: N/A"
                genreTextView.text = "Genre: N/A"
                countryTextView.text = "Country: N/A"
                ratingTextView.text = "Rating: N/A"
                releasedTextView.text = "Released: N/A"
                runtimeTextView.text = "Runtime: N/A"
                writerTextView.text = "Writer: N/A"
                actorsTextView.text = "Actors: N/A"
                plotTextView.text = "Plot: N/A"
                languageTextView.text = "Language: N/A"
                awardsTextView.text = "Awards: N/A"
            }
        })

        movie.posterUrl?.let {
            Picasso.get().load(it).into(posterImageView)
        }

        findViewById<Button>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        findViewById<Button>(R.id.trailerButton).setOnClickListener  {
            val searchQuery = movie.title?.replace(" ", "+") ?: ""
            val trailerUrl = "https://www.youtube.com/results?search_query=$searchQuery"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl))
            startActivity(intent)
        }
    }
}

