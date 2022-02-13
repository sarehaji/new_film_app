package com.example.jetpacksub3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpacksub3.data.source.DataDetail
import com.example.jetpacksub3.data.source.DataFilm
import com.example.jetpacksub3.data.source.CatalogRepository
import com.example.jetpacksub3.data.source.local.entity.DataMovie
import com.example.jetpacksub3.data.source.local.entity.DataShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val catalogRepository: CatalogRepository) : ViewModel() {
    private lateinit var dataDetail: LiveData<DataDetail>

    fun getDetailInformation(id: Int, state: Int): LiveData<DataDetail> {
        dataDetail = if (state == 1) catalogRepository.getMovieDetail(id)
        else catalogRepository.getTvDetail(id)
        return dataDetail
    }

    fun getFavorite(filmId: Int, state: Int): LiveData<DataFilm?> {
        return if (state == 1) catalogRepository.checkFavMove(filmId)
        else catalogRepository.checkFavShow(filmId)
    }

    fun insertFav(dataFilm: DataFilm, state: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (state == 1) {
                val favoriteMovie = DataMovie(
                    dataFilm.id,
                    dataFilm.title,
                    dataFilm.releaseDate,
                    dataFilm.overView,
                    dataFilm.imagePath,
                    dataFilm.voteAverage,
                    dataFilm.voteCount,
                    dataFilm.popularity
                )
                catalogRepository.insertMovie(favoriteMovie)
            } else {
                val favoriteShow = DataShow(
                    dataFilm.id,
                    dataFilm.title,
                    dataFilm.releaseDate,
                    dataFilm.overView,
                    dataFilm.imagePath,
                    dataFilm.voteAverage,
                    dataFilm.voteCount,
                    dataFilm.popularity
                )
                catalogRepository.insertShow(favoriteShow)
            }
        }
    }

    fun deleteFav(dataFilm: DataFilm, state: Int){
        viewModelScope.launch(Dispatchers.IO){
            if (state == 1){
                val favoriteMovie = DataMovie(
                    dataFilm.id,
                    dataFilm.title,
                    dataFilm.releaseDate,
                    dataFilm.overView,
                    dataFilm.imagePath,
                    dataFilm.voteAverage,
                    dataFilm.voteCount,
                    dataFilm.popularity
                )
                catalogRepository.deleteMovie(favoriteMovie)
            } else {
                val favoriteShow = DataShow(
                    dataFilm.id,
                    dataFilm.title,
                    dataFilm.releaseDate,
                    dataFilm.overView,
                    dataFilm.imagePath,
                    dataFilm.voteAverage,
                    dataFilm.voteCount,
                    dataFilm.popularity
                )
                catalogRepository.deleteShow(favoriteShow)
            }
        }
    }
}