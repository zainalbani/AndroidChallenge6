package com.example.challenge5.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challenge5.databinding.ItemMovieBinding
import com.example.challenge5.model.GetNowPlayingResponseItem
import com.example.challenge5.ui.DetailMovieActivity

class MovieAdapter(
    private val listMovie: ArrayList<GetNowPlayingResponseItem>,
    private val context: Context
):RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView:ItemMovieBinding):RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500"
        fun bind(movieData: GetNowPlayingResponseItem) {
            with(binding) {
                Glide.with(itemView)
                    .load(IMAGE_BASE + movieData.posterPath)
                    .into(imgPoster)
                tvTitle.text = movieData.title
                tvTahun.text = movieData.releaseDate

                cvIdMovie.setOnClickListener {
                    var id = Intent(context, DetailMovieActivity::class.java).apply {
                        putExtra("id", movieData.id)
                    }
                    context.startActivity(id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(ItemMovieBinding.inflate(LayoutInflater.from(parent.context),
            parent, false
        ))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    override fun getItemCount(): Int = listMovie.size
}