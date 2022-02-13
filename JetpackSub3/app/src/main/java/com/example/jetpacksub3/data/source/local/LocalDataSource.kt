package com.example.jetpacksub3.data.source.local

import androidx.paging.DataSource
import com.example.jetpacksub3.data.source.DataFilm
import com.example.jetpacksub3.data.source.local.entity.DataMovie
import com.example.jetpacksub3.data.source.local.entity.DataShow
import com.example.jetpacksub3.data.source.local.room.CatalogueDao
import com.example.jetpacksub3.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalDataSource private constructor(private val catalogueDao: CatalogueDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(catalogueDao: CatalogueDao): LocalDataSource = INSTANCE ?: LocalDataSource(catalogueDao)
    }

    fun insertMovie(movie: DataMovie) = catalogueDao.insertMovie(movie)
    fun insertShow(show: DataShow) = catalogueDao.insertShow(show)
    fun deleteMovie(movie: DataMovie) = catalogueDao.deleteMovie(movie)
    fun deleteShow(show: DataShow) = catalogueDao.deleteShow(show)

    fun getFavoriteMovies(): DataSource.Factory<Int, DataFilm>{
        EspressoIdlingResource.increment()
        val dataSource = catalogueDao.getFavMovies()
        EspressoIdlingResource.decrement()
        return dataSource
    }
    fun getFavoriteShows(): DataSource.Factory<Int, DataFilm>{
        EspressoIdlingResource.increment()
        val dataSource = catalogueDao.getFavShows()
        EspressoIdlingResource.decrement()
        return dataSource
    }
    fun checkFavoriteMovie(callback: LoadFavoriteMovie, id: Int) {
        EspressoIdlingResource.increment()
        CoroutineScope(Dispatchers.IO).launch {
            callback.onFavReceived(catalogueDao.checkFavMovie(id))
            EspressoIdlingResource.decrement()
        }
    }

    fun checkFavoriteShow(callback: LoadFavoriteShow, id: Int){
        EspressoIdlingResource.increment()
        CoroutineScope(Dispatchers.IO).launch {
            callback.onFavReceived(catalogueDao.checkFavShow(id))
            EspressoIdlingResource.decrement()
        }
    }

    interface LoadFavoriteMovie {
        fun onFavReceived(dataFilm: DataFilm?)
    }

    interface LoadFavoriteShow {
        fun onFavReceived(dataFilm: DataFilm?)
    }
}