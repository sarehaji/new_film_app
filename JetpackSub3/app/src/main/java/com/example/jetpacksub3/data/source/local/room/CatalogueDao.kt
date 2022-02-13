package com.example.jetpacksub3.data.source.local.room

import androidx.paging.DataSource
import androidx.room.*
import com.example.jetpacksub3.data.source.DataFilm
import com.example.jetpacksub3.data.source.local.entity.DataMovie
import com.example.jetpacksub3.data.source.local.entity.DataShow


@Dao
interface CatalogueDao {

    @Insert(entity = DataMovie::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(dataMovies: DataMovie)

    @Insert(entity = DataShow::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertShow(dataShow: DataShow)

    @Delete(entity = DataMovie::class)
    fun deleteMovie(dataMovies: DataMovie)

    @Delete(entity = DataShow::class)
    fun deleteShow(dataShow: DataShow)

    @Query("SELECT * from favorite_movie_table")
    fun getFavMovies(): DataSource.Factory<Int, DataFilm>

    @Query("SELECT * from favorite_show_table")
    fun getFavShows(): DataSource.Factory<Int, DataFilm>

    @Query("SELECT * from favorite_movie_table WHERE id LIKE :searchID")
    fun checkFavMovie(searchID: Int): DataFilm

    @Query("SELECT * from favorite_show_table WHERE id LIKE :searchID")
    fun checkFavShow(searchID: Int): DataFilm
}