package com.example.jetpacksub3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.jetpacksub3.data.source.DataFilm
import com.example.jetpacksub3.data.source.CatalogRepository

class MainViewModel(private val catalogRepository: CatalogRepository) : ViewModel() {

    fun getMovies(): LiveData<List<DataFilm>> = catalogRepository.getMovies()
    fun getShows(): LiveData<List<DataFilm>> = catalogRepository.getTvShows()
}