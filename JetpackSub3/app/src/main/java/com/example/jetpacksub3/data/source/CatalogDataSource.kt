package com.example.jetpacksub3.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.jetpacksub3.data.source.local.entity.DataMovie
import com.example.jetpacksub3.data.source.local.entity.DataShow

interface CatalogDataSource {
    fun getMovies(): LiveData<List<DataFilm>>
    fun getTvShows(): LiveData<List<DataFilm>>
    fun getMovieDetail(id: Int): LiveData<DataDetail>
    fun getTvDetail(id: Int): LiveData<DataDetail>
    fun insertMovie(movie: DataMovie)
    fun insertShow(show: DataShow)
    fun deleteMovie(movie: DataMovie)
    fun deleteShow(show: DataShow)
    fun getFavMovies(): LiveData<PagedList<DataFilm>>
    fun getFavShows(): LiveData<PagedList<DataFilm>>
    fun checkFavMove(id: Int): LiveData<DataFilm?>
    fun checkFavShow(id: Int): LiveData<DataFilm?>
}