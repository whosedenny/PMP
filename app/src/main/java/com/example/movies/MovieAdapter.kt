package com.example.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val context: Context,
    private val movies: List<Movie>
) : ArrayAdapter<Movie>(context, R.layout.item_movie, movies) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = convertView ?: inflater.inflate(R.layout.item_movie, parent, false)

        val movie = movies[position]
        itemView.findViewById<TextView>(R.id.titleTextView).text = movie.title
        itemView.findViewById<TextView>(R.id.yearTextView).text = movie.year
        val posterImageView = itemView.findViewById<ImageView>(R.id.posterImageView)
        movie.posterUrl?.let {
            Picasso.get().load(it).into(posterImageView)
        }

        return itemView
    }
}

