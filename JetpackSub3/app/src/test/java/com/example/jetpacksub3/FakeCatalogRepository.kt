package com.example.jetpacksub3

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.jetpacksub3.data.source.CatalogDataSource
import com.example.jetpacksub3.data.source.DataDetail
import com.example.jetpacksub3.data.source.DataFilm
import com.example.jetpacksub3.data.source.local.LocalDataSource
import com.example.jetpacksub3.data.source.local.entity.DataMovie
import com.example.jetpacksub3.data.source.local.entity.DataShow
import com.example.jetpacksub3.data.source.remote.RemoteDataSource
import com.example.jetpacksub3.data.source.remote.response.DetailResponse
import com.example.jetpacksub3.data.source.remote.response.FilmResponse

class FakeCatalogRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CatalogDataSource {


    override fun getMovies(): LiveData<List<DataFilm>> {
        val movieResults = MutableLiveData<List<DataFilm>>()
        remoteDataSource.getMovies(object : RemoteDataSource.LoadMoviesCallback {
            override fun onMoviesReceived(movieResponses: List<FilmResponse>) {
                val movieList = ArrayList<DataFilm>()

                for (response in movieResponses.indices) {
                    val movie = DataFilm(
                        movieResponses[response].id,
                        movieResponses[response].title,
                        movieResponses[response].releaseDate,
                        movieResponses[response].overView,
                        movieResponses[response].imagePath,
                        movieResponses[response].voteAverage,
                        movieResponses[response].voteCount,
                        movieResponses[response].popularity
                    )
                    Log.d("dataSource", movie.toString())
                    movieList.add(movie)
                }
                Log.d("dataSource", movieList.toString())
                movieResults.value = movieList
            }
        })
        return movieResults
    }

    override fun getTvShows(): LiveData<List<DataFilm>> {
        val tvShowResults = MutableLiveData<List<DataFilm>>()
        remoteDataSource.getTvShows(object : RemoteDataSource.LoadTvShowsCallback {
            override fun onTvShowsReceived(tvShowResponses: List<FilmResponse>) {
                val tvShowList = ArrayList<DataFilm>()
                for (response in tvShowResponses.indices) {
                    val tvShow = DataFilm(
                        tvShowResponses[response].id,
                        tvShowResponses[response].title,
                        tvShowResponses[response].releaseDate,
                        tvShowResponses[response].overView,
                        tvShowResponses[response].imagePath,
                        tvShowResponses[response].voteAverage,
                        tvShowResponses[response].voteCount,
                        tvShowResponses[response].popularity
                    )
                    tvShowList.add(tvShow)
                }
                tvShowResults.value = tvShowList
            }
        })
        return tvShowResults
    }

    override fun getMovieDetail(id: Int): LiveData<DataDetail> {
        val movieDetailResult = MutableLiveData<DataDetail>()
        remoteDataSource.getMovieDetail(object : RemoteDataSource.LoadMovieDetailCallback {
            override fun onDetailReceived(detailResponse: DetailResponse) {
                val details = DataDetail(
                    detailResponse.genres,
                    null,
                    detailResponse.runTime,
                    detailResponse.tagLine
                )
                movieDetailResult.value = details
            }
        }, id)
        return movieDetailResult
    }

    override fun getTvDetail(id: Int): LiveData<DataDetail> {
        val tvDetailResult = MutableLiveData<DataDetail>()
        remoteDataSource.getTvDetail(object : RemoteDataSource.LoadTvDetailCallback {
            override fun onDetailReceived(detailResponse: DetailResponse) {
                val details = DataDetail(
                    detailResponse.genres,
                    detailResponse.runTimes,
                    null,
                    null
                )
                tvDetailResult.value = details
            }
        }, id)
        return tvDetailResult
    }

    override fun insertMovie(movie: DataMovie) {
        localDataSource.insertMovie(movie)
    }

    override fun insertShow(show: DataShow) {
        localDataSource.insertShow(show)
    }

    override fun deleteMovie(movie: DataMovie) {
        localDataSource.deleteMovie(movie)
    }

    override fun deleteShow(show: DataShow) {
        localDataSource.deleteShow(show)
    }

    override fun getFavMovies(): LiveData<PagedList<DataFilm>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(6)
            .setPageSize(6)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun getFavShows(): LiveData<PagedList<DataFilm>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(6)
            .setPageSize(6)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteShows(), config).build()
    }

    override fun checkFavMove(id: Int): LiveData<DataFilm?> {
        val favorite = MutableLiveData<DataFilm?>()
        localDataSource.checkFavoriteMovie(object : LocalDataSource.LoadFavoriteMovie{
            override fun onFavReceived(dataFilm: DataFilm?) {
                favorite.postValue(dataFilm)
            }
        }, id)
        return favorite
    }

    override fun checkFavShow(id: Int): LiveData<DataFilm?> {
        val favorite = MutableLiveData<DataFilm?>()
        localDataSource.checkFavoriteShow(object : LocalDataSource.LoadFavoriteShow{
            override fun onFavReceived(dataFilm: DataFilm?) {
                favorite.postValue(dataFilm)
            }
        }, id)
        return favorite
    }
}