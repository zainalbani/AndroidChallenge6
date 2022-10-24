package com.example.challenge5.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.example.challenge5.R
import com.example.challenge5.databinding.ActivityDetailMovieBinding
import com.example.challenge5.model.MovieDetailData
import com.example.challenge5.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMovieActivity : AppCompatActivity() {
    private var _binding : ActivityDetailMovieBinding? = null
    private val binding get() = _binding!!
    var b : Bundle? = null
    private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        b = intent.extras
        val id = b?.getInt("id")

        ApiClient.instance.getDetailMovie(id)
            .enqueue(object : Callback<MovieDetailData>{
                override fun onResponse(
                    call: Call<MovieDetailData>,
                    response: Response<MovieDetailData>
                ) {
                    binding.tvRelease.text = response.body()?.releaseDate
                    binding.tvJudul.text = response.body()?.title
                    binding.tvOverview.text = response.body()?.overview

                    Glide.with(this@DetailMovieActivity)
                        .load(IMAGE_BASE + response.body()?.posterPath)
                        .into(binding.imgDetailPoster)
                }

                override fun onFailure(call: Call<MovieDetailData>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            }
            )
    }
}