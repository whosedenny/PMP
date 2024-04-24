package com.example.movies

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var searchButton: Button
    private lateinit var movieTitleEditText: EditText
    private lateinit var postersContainer: LinearLayout
    private lateinit var postersContainer2: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton = findViewById(R.id.searchButton)
        movieTitleEditText = findViewById(R.id.movieTitleEditText)
        postersContainer = findViewById(R.id.postersContainer)
        postersContainer2 = findViewById(R.id.postersContainer2)
        val facebookButton = findViewById<Button>(R.id.facebookButton)
        val twitterButton = findViewById<Button>(R.id.twitterButton)
        val instagramButton = findViewById<Button>(R.id.instagramButton)

        facebookButton.setOnClickListener {
            openSocialMedia("https://www.facebook.com/imdb")
        }
        twitterButton.setOnClickListener {
            openSocialMedia("https://twitter.com/imdb")
        }
        instagramButton.setOnClickListener {
            openSocialMedia("https://www.instagram.com/imdb")
        }


        searchButton.setOnClickListener {
            searchMovies()
        }

        loadImages()
    }

    private fun searchMovies() {
        val movieTitle = movieTitleEditText.text.toString().trim()
        if (movieTitle.isEmpty()) {
            Toast.makeText(this, "Enter the name of the movie", Toast.LENGTH_SHORT).show()
            return
        }

        val service = OMDbApiClient.getClient().create(OMDbService::class.java)
        val call = service.searchMovies("5a85c2c2", movieTitle)
        call.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    val searchResponse = response.body()
                    if (searchResponse != null && !searchResponse.searchResults.isNullOrEmpty()) {
                        val movies = searchResponse.searchResults
                        val intent = Intent(this@MainActivity, MovieListActivity::class.java)
                        intent.putExtra("movies", Gson().toJson(movies))
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@MainActivity, "No movies found", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error getting data", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Network error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun loadImages() {
        val imageResources = arrayOf(
            Pair(R.drawable.fast_1, "https://www.imdb.com/title/tt0232500/?ref_=fn_al_tt_1"),
            Pair(R.drawable.fast_2, "https://www.imdb.com/title/tt0322259/?ref_=fn_al_tt_3"),
            Pair(R.drawable.fast_3, "https://www.imdb.com/title/tt0463985/?ref_=fn_al_tt_11"),
            Pair(R.drawable.fast_4, "https://www.imdb.com/title/tt1013752/?ref_=fn_al_tt_4"),
            Pair(R.drawable.fast_5, "https://www.imdb.com/title/tt1596343/?ref_=fn_al_tt_6"),
            Pair(R.drawable.fast_6, "https://www.imdb.com/title/tt1905041/?ref_=fn_al_tt_5"),
            Pair(R.drawable.fast_7, "https://www.imdb.com/title/tt2820852/?ref_=fn_al_tt_1"),
            Pair(R.drawable.fast_8, "https://www.imdb.com/title/tt4630562/?ref_=fn_al_tt_1"),
            Pair(R.drawable.fast_9, "https://www.imdb.com/title/tt5433138/?ref_=fn_al_tt_3"),
            Pair(R.drawable.fast_10, "https://www.imdb.com/title/tt5433140/?ref_=fn_al_tt_1"),
            Pair(
                R.drawable.fast_hobbs_shaw,
                "https://www.imdb.com/title/tt6806448/?ref_=fn_al_tt_10"
            ),
        )

        for ((resourceId, videoUrl) in imageResources) {
            val imageView = ImageView(this)
            imageView.setImageResource(resourceId)
            imageView.layoutParams = LinearLayout.LayoutParams(
                resources.getDimensionPixelSize(R.dimen.poster_width),
                resources.getDimensionPixelSize(R.dimen.poster_height)
            )
            val margin = resources.getDimensionPixelSize(R.dimen.poster_margin)
            val layoutParams = imageView.layoutParams as LinearLayout.LayoutParams
            layoutParams.setMargins(margin, 0, margin, 0)
            imageView.layoutParams = layoutParams

            imageView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                startActivity(intent)
            }

            postersContainer.addView(imageView)
        }
        val imageResources2 = arrayOf(
            Pair(R.drawable.movie_1, "https://www.imdb.com/title/tt1129442/?ref_=fn_al_tt_1"),
            Pair(R.drawable.movie_2, "https://www.imdb.com/title/tt2231461/?ref_=fn_al_tt_1"),
            Pair(R.drawable.movie_3, "https://www.imdb.com/title/tt2126355/?ref_=fn_al_tt_1"),
            Pair(R.drawable.movie_4, "https://www.imdb.com/title/tt5090568/?ref_=fn_al_tt_1"),
            Pair(R.drawable.movie_5, "https://www.imdb.com/title/tt2283362/?ref_=fn_al_tt_1"),
        )

        for ((resourceId, videoUrl) in imageResources2) {
            val imageView = ImageView(this)
            imageView.setImageResource(resourceId)
            imageView.layoutParams = LinearLayout.LayoutParams(
                resources.getDimensionPixelSize(R.dimen.poster_width),
                resources.getDimensionPixelSize(R.dimen.poster_height)
            )
            val margin = resources.getDimensionPixelSize(R.dimen.poster_margin)
            val layoutParams = imageView.layoutParams as LinearLayout.LayoutParams
            layoutParams.setMargins(margin, 0, margin, 0)
            imageView.layoutParams = layoutParams

            imageView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                startActivity(intent)
            }

            postersContainer2.addView(imageView)
        }

    }
    fun openSocialMedia(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}



