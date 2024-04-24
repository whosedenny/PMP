package com.example.movies

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class MovieListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        val jsonMovies = intent.getStringExtra("movies")
        val movies = Gson().fromJson(jsonMovies, Array<Movie>::class.java).toList()

        val listView = findViewById<ListView>(R.id.moviesListView)
        val adapter = MovieAdapter(this, movies)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val movie = movies[position]
            val intent = Intent(this, MovieDetailActivity::class.java).apply {
                putExtra("movie", Gson().toJson(movie))
                putExtra("director", movie.director)
                putExtra("genre", movie.genre)
                putExtra("country", movie.country)
                putExtra("rating", movie.rating)
            }
            startActivity(intent)
        }
        findViewById<Button>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

    }
}





