package com.example.jetpacksub3.data.source

data class DataDetail(
    var genres : ArrayList<String>,
    var runTimes: ArrayList<Int>?,
    var runTime: Int?,
    val tagLine: String?
)