package com.example.jetpacksub3.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.jetpacksub3.data.source.CatalogRepository
import com.example.jetpacksub3.data.source.DataFilm
import com.example.jetpacksub3.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogRepository: CatalogRepository

    @Mock
    private lateinit var observer: Observer<List<DataFilm>>

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(catalogRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = DataDummy.generateDummyMovies()
        val movieData = MutableLiveData<List<DataFilm>>()
        movieData.value = dummyMovies

        Mockito.`when`(catalogRepository.getMovies()).thenReturn(movieData)
        val movies = viewModel.getMovies().value
        verify<CatalogRepository>(catalogRepository).getMovies()
        assertNotNull(movies)
        assertEquals(5, movies?.size)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getShows(){
        val dummyShows = DataDummy.generateDummyShows()
        val showData = MutableLiveData<List<DataFilm>>()
        showData.value = dummyShows

        Mockito.`when`(catalogRepository.getTvShows()).thenReturn(showData)
        val shows = viewModel.getShows().value
        verify<CatalogRepository>(catalogRepository).getTvShows()
        assertNotNull(shows)
        assertEquals(5, shows?.size)

        viewModel.getShows().observeForever(observer)
        verify(observer).onChanged(dummyShows)
    }
}