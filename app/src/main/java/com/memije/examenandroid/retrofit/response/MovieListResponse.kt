package com.memije.examenandroid.retrofit.response

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("page") var page: Int,
    @SerializedName("results") var results: List<MovieResponse>,
    @SerializedName("total_pages") var total_pages: Int,
    @SerializedName("total_results") var total_results: Int,
)