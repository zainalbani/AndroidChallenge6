package com.example.challenge5.network

import com.example.challenge5.model.GetNowPlayingResponse
import com.example.challenge5.model.MovieDetailData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET ("movie/now_playing?api_key=a0ca53cb305db9a12b6c99174d34dbd4&language=en-US&page=1")
    fun getNowPlaying(): Call<GetNowPlayingResponse>

    @GET ("movie/{movie_id}?api_key=a0ca53cb305db9a12b6c99174d34dbd4&language=en-US&page=1")
    fun getDetailMovie(
        @Path("movie_id")id:Int?,
    ) : Call<MovieDetailData>
}