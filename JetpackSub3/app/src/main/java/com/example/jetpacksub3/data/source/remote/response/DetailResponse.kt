package com.example.jetpacksub3.data.source.remote.response

data class DetailResponse(
    var genres : ArrayList<String>,
    var runTimes: ArrayList<Int>?,
    var runTime: Int?,
    var tagLine: String?
)