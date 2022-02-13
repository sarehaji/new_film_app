package com.example.jetpacksub3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.jetpacksub3.data.source.DataFilm
import com.example.jetpacksub3.data.source.CatalogRepository

class FavoriteViewModel(private val catalogRepository: CatalogRepository) : ViewModel() {

    fun getListFavorite(state: Int): LiveData<PagedList<DataFilm>> {
        return if (state == 1) catalogRepository.getFavMovies()
        else catalogRepository.getFavShows()
    }
}