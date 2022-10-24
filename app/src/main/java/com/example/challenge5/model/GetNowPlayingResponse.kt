package com.example.challenge5.model

import com.google.gson.annotations.SerializedName

data class GetNowPlayingResponse(
    @SerializedName("results") val data:List<GetNowPlayingResponseItem>,
    @SerializedName("total_results") val totalData:Int
)
