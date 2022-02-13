package com.example.jetpacksub3

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.example.jetpacksub3.data.source.DataFilm
import com.example.jetpacksub3.data.source.local.LocalDataSource
import com.example.jetpacksub3.data.source.remote.RemoteDataSource
import com.example.jetpacksub3.utils.DataDummy
import com.example.jetpacksub3.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class CatalogRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val catalogRepository = FakeCatalogRepository(remote, local)
    private val movieResponses = DataDummy.generateDummyMovieResponses()
    private val showResponses = DataDummy.generateDummyShowResponses()
    private val movieDetailResponse = DataDummy.generateDummyMovieDetailsResponse()
    private val showDetailResponse = DataDummy.generateDummyShowDetailsResponse()
    private val movieId = movieResponses[0].id
    private val tvId = showResponses[0].id

    @Test
    fun getAllMovies() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadMoviesCallback).onMoviesReceived(movieResponses)
            null
        }.`when`(remote).getMovies(any())
        val movies = LiveDataTestUtil.getValue(catalogRepository.getMovies())
        verify(remote).getMovies(any())
        assertNotNull(movies)
        assertEquals(movieResponses.size, movies.size)
    }

    @Test
    fun getAllShows() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadTvShowsCallback).onTvShowsReceived(showResponses)
            null
        }.`when`(remote).getTvShows(any())
        val shows = LiveDataTestUtil.getValue(catalogRepository.getTvShows())
        verify(remote).getTvShows(any())
        assertNotNull(shows)
        assertEquals(movieResponses.size, shows.size)
    }

    @Test
    fun getMovieDetails(){
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadMovieDetailCallback).onDetailReceived(movieDetailResponse)
            null
        }.`when`(remote).getMovieDetail(any(), eq(movieId))
        val movieDetails = LiveDataTestUtil.getValue(catalogRepository.getMovieDetail(movieId))
        verify(remote).getMovieDetail(any(), eq(movieId))
        assertNotNull(movieDetails)
        assertEquals(movieDetailResponse.genres, movieDetails.genres)
        assertEquals(movieDetailResponse.tagLine, movieDetails.tagLine)
        assertEquals(movieDetailResponse.runTime, movieDetails.runTime)
        assertEquals(movieDetailResponse.runTimes, movieDetails.runTimes)
    }

    @Test
    fun getShowDetails(){
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadTvDetailCallback).onDetailReceived(showDetailResponse)
            null
        }.`when`(remote).getTvDetail(any(), eq(tvId))
        val showDetails = LiveDataTestUtil.getValue(catalogRepository.getTvDetail(tvId))
        verify(remote).getTvDetail(any(), eq(tvId))
        assertNotNull(showDetails)
        assertEquals(showDetailResponse.genres, showDetails.genres)
        assertEquals(showDetailResponse.tagLine, showDetails.tagLine)
        assertEquals(showDetailResponse.runTime, showDetails.runTime)
        assertEquals(showDetailResponse.runTimes, showDetails.runTimes)
    }

    @Test
    fun getFavoriteMovies(){
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, DataFilm>
        Mockito.`when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        catalogRepository.getFavMovies()
        val favoriteMovies = Utils.mockPagedList(DataDummy.generateDummyMovies())
        verify(local).getFavoriteMovies()
        assertNotNull(favoriteMovies)
        assertEquals(movieResponses.size.toLong(), favoriteMovies.size.toLong())
    }

    @Test
    fun getFavoriteShows(){
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, DataFilm>
        Mockito.`when`(local.getFavoriteShows()).thenReturn(dataSourceFactory)
        catalogRepository.getFavShows()
        val favoriteShows = Utils.mockPagedList(DataDummy.generateDummyShows())
        verify(local).getFavoriteShows()
        assertNotNull(favoriteShows)
        assertEquals(showResponses.size.toLong(), favoriteShows.size.toLong())
    }

    @Test
    fun checkFavoriteMovie(){
        val dummyMovie = DataDummy.generateDummyMovies()[0]
        doAnswer { invocation ->
            (invocation.arguments[0] as LocalDataSource.LoadFavoriteMovie).onFavReceived(dummyMovie)
            null
        }.`when`(local).checkFavoriteMovie(any(), eq(movieId))
        val favoriteMovie = LiveDataTestUtil.getValue(catalogRepository.checkFavMove(movieId))
        verify(local).checkFavoriteMovie(any(), eq(movieId))
        assertNotNull(favoriteMovie)
        assertEquals(dummyMovie.id, favoriteMovie?.id)
        assertEquals(dummyMovie.title, favoriteMovie?.title)
        assertEquals(dummyMovie.imagePath, favoriteMovie?.imagePath)
        assertEquals(dummyMovie.overView, favoriteMovie?.overView)
        assertEquals(dummyMovie.releaseDate, favoriteMovie?.releaseDate)
        assertEquals(dummyMovie.voteCount, favoriteMovie?.voteCount)
        assertEquals(dummyMovie.popularity, favoriteMovie?.popularity)
    }

    @Test
    fun checkFavoriteShow(){
        val dummyShow = DataDummy.generateDummyShows()[0]
        doAnswer { invocation ->
            (invocation.arguments[0] as LocalDataSource.LoadFavoriteShow).onFavReceived(dummyShow)
            null
        }.`when`(local).checkFavoriteShow(any(), eq(tvId))
        val favoriteShow = LiveDataTestUtil.getValue(catalogRepository.checkFavShow(tvId))
        verify(local).checkFavoriteShow(any(), eq(tvId))
        assertNotNull(favoriteShow)
        assertEquals(dummyShow.id, favoriteShow?.id)
        assertEquals(dummyShow.title, favoriteShow?.title)
        assertEquals(dummyShow.imagePath, favoriteShow?.imagePath)
        assertEquals(dummyShow.overView, favoriteShow?.overView)
        assertEquals(dummyShow.releaseDate, favoriteShow?.releaseDate)
        assertEquals(dummyShow.voteCount, favoriteShow?.voteCount)
        assertEquals(dummyShow.popularity, favoriteShow?.popularity)
    }
}