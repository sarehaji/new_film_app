package com.example.jetpacksub3.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie_table")
data class DataMovie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String?,
    @ColumnInfo(name = "releaseDate")
    var releaseDate: String?,
    @ColumnInfo(name = "overView")
    var overview: String?,
    @ColumnInfo(name = "imagePath")
    var imagePath: String?,
    @ColumnInfo(name = "voteAverage")
    var voteAverage: Double,
    @ColumnInfo(name = "voteCount")
    var voteCount: Int,
    @ColumnInfo(name = "popularity")
    var popularity: Int
)