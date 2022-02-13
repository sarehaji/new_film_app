package com.example.jetpacksub3.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.jetpacksub3.data.source.CatalogRepository
import com.example.jetpacksub3.data.source.DataDetail
import com.example.jetpacksub3.data.source.DataFilm
import com.example.jetpacksub3.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogRepository: CatalogRepository

    @Mock
    private lateinit var observer: Observer<DataDetail>

    @Mock
    private lateinit var favoriteObserver: Observer<DataFilm?>

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        viewModel = DetailViewModel(catalogRepository)
    }

    @Test
    fun getMovieDetails(){
        val dummyMovie = DataDummy.generateDummyMovies()[0]
        val dummyDetailsData = MutableLiveData<DataDetail>()
        val dummyDetails = DataDummy.generateDummyMovieDetails()
        dummyDetailsData.value = dummyDetails

        Mockito.`when`(catalogRepository.getMovieDetail(dummyMovie.id)).thenReturn(dummyDetailsData)
        val movieDetails = viewModel.getDetailInformation(dummyMovie.id, 1).value as DataDetail
        verify(catalogRepository).getMovieDetail(dummyMovie.id)
        assertNotNull(movieDetails)
        assertEquals(dummyDetails.genres, movieDetails.genres)
        assertEquals(dummyDetails.runTime, movieDetails.runTime)
        assertEquals(dummyDetails.runTimes, movieDetails.runTimes)
        assertEquals(dummyDetails.tagLine, movieDetails.tagLine)

        viewModel.getDetailInformation(dummyMovie.id, 1).observeForever(observer)
        verify(observer).onChanged(dummyDetails)
    }

    @Test
    fun getShowDetails(){
        val dummyShow = DataDummy.generateDummyShows()[0]
        val dummyDetailsData = MutableLiveData<DataDetail>()
        val dummyDetails = DataDummy.generateDummyShowDetails()
        dummyDetailsData.value = dummyDetails

        Mockito.`when`(catalogRepository.getTvDetail(dummyShow.id)).thenReturn(dummyDetailsData)
        val showDetails = viewModel.getDetailInformation(dummyShow.id, 2).value as DataDetail
        verify(catalogRepository).getTvDetail(dummyShow.id)
        assertNotNull(showDetails)
        assertEquals(dummyDetails.genres, showDetails.genres)
        assertEquals(dummyDetails.runTimes, showDetails.runTimes)
        assertEquals(dummyDetails.runTime, showDetails.runTime)
        assertEquals(dummyDetails.tagLine, showDetails.tagLine)

        viewModel.getDetailInformation(dummyShow.id, 2).observeForever(observer)
        verify(observer).onChanged(dummyDetails)
    }

    @Test
    fun getFavorite(){
        val dummyMovie = DataDummy.generateDummyMovies()[0]
        val dummyFavorite = MutableLiveData<DataFilm>()
        dummyFavorite.value = dummyMovie

        Mockito.`when`(catalogRepository.checkFavMove(dummyMovie.id)).thenReturn(dummyFavorite)
        val favorite = viewModel.getFavorite(dummyMovie.id, 1).value as DataFilm
        verify(catalogRepository).checkFavMove(dummyMovie.id)
        assertNotNull(favorite)
        assertEquals(dummyMovie.id, favorite.id)
        assertEquals(dummyMovie.imagePath, favorite.imagePath)
        assertEquals(dummyMovie.overView, favorite.overView)
        assertEquals(dummyMovie.popularity, favorite.popularity)
        assertEquals(dummyMovie.releaseDate, favorite.releaseDate)
        assertEquals(dummyMovie.title, favorite.title)
        assertEquals(dummyMovie.voteCount, favorite.voteCount)

        viewModel.getFavorite(dummyMovie.id, 1).observeForever(favoriteObserver)
        verify(favoriteObserver).onChanged(dummyMovie)
    }
}