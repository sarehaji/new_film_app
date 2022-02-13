package com.example.jetpacksub3.data.source.remote

import android.util.Log
import com.example.jetpacksub3.data.source.remote.response.DetailResponse
import com.example.jetpacksub3.data.source.remote.response.FilmResponse
import com.example.jetpacksub3.utils.EspressoIdlingResource
import com.example.jetpacksub3.utils.Connection

class RemoteDataSource private constructor(private  val connection: Connection){

    companion object{
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(j: Connection): RemoteDataSource = instance ?: synchronized(this){
            instance ?: RemoteDataSource(j)
        }
    }

    fun getMovies(callback: LoadMoviesCallback){
        EspressoIdlingResource.increment()
        connection.getMovies(object : Connection.GetMovieCallback{
            override fun onFinishedRetrieving(movieResponse: List<FilmResponse>) {
                Log.d("dataSource", movieResponse.toString())
                callback.onMoviesReceived(movieResponse)
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun getTvShows(callback: LoadTvShowsCallback){
        EspressoIdlingResource.increment()
        connection.getTvShows(object : Connection.GetTvShowsCallback{
            override fun onFinishedRetrieving(tvShowResponse: List<FilmResponse>) {
                callback.onTvShowsReceived(tvShowResponse)
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun getMovieDetail(callback: LoadMovieDetailCallback, id: Int){
        EspressoIdlingResource.increment()
        connection.getMovieDetail(object : Connection.GetMovieDetailCallback{
            override fun onFinishedRetrieving(detailResponse: DetailResponse) {
                callback.onDetailReceived(detailResponse)
                EspressoIdlingResource.decrement()
            }
        }, id)
    }

    fun getTvDetail(callback: LoadTvDetailCallback, id: Int){
        EspressoIdlingResource.increment()
        connection.getTvShowDetail(object : Connection.GetTvShowDetailCallback{
            override fun onFinishedRetrieving(detailResponse: DetailResponse) {
                callback.onDetailReceived(detailResponse)
                EspressoIdlingResource.decrement()
            }
        }, id)
    }

    interface LoadMoviesCallback {
        fun onMoviesReceived(movieResponses: List<FilmResponse>)
    }

    interface LoadTvShowsCallback {
        fun onTvShowsReceived(tvShowResponses: List<FilmResponse>)
    }

    interface LoadMovieDetailCallback {
        fun onDetailReceived(detailResponse: DetailResponse)
    }

    interface LoadTvDetailCallback {
        fun onDetailReceived(detailResponse: DetailResponse)
    }
}