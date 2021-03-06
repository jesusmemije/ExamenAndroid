package com.memije.examenandroid.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.memije.examenandroid.R
import com.memije.examenandroid.databinding.MovieItemBinding
import com.memije.examenandroid.room.entity.MovieEntity
import com.squareup.picasso.Picasso

class MovieAdapter(movieList: List<MovieEntity>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var mMovieList: List<MovieEntity> = movieList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Pasamos el objeto de MovieResponse
        holder.movie = mMovieList[position]
        // Iniciamos la función data
        holder.initData(holder.movie)
    }

    override fun getItemCount(): Int {
        return mMovieList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val vBinding = MovieItemBinding.bind(itemView)
        lateinit var movie: MovieEntity

        fun initData(mMovie: MovieEntity) {
            vBinding.movieTitle.text = mMovie.title
            vBinding.movieReleaseDate.text = mMovie.releaseDate

            Picasso.get()
                .load("https://image.tmdb.org/t/p/w500" + mMovie.backdropPath)
                .into(vBinding.moviePoster)

        }
    }
}