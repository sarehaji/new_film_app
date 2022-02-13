package com.example.jetpacksub3.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.jetpacksub3.data.source.CatalogRepository
import com.example.jetpacksub3.data.source.DataFilm
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
class FavoriteViewModelTest{

    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogRepository: CatalogRepository

    @Mock
    private lateinit var observer: Observer<PagedList<DataFilm>>

    @Mock
    private lateinit var pagedLst: PagedList<DataFilm>

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        viewModel = FavoriteViewModel(catalogRepository)
    }

    @Test
    fun getFavorites(){
        val dummyFavorites = pagedLst
        Mockito.`when`(dummyFavorites.size).thenReturn(10)
        val dummyFavoriteData = MutableLiveData<PagedList<DataFilm>>()
        dummyFavoriteData.value = dummyFavorites

        Mockito.`when`(catalogRepository.getFavMovies()).thenReturn(dummyFavoriteData)
        val movies = viewModel.getListFavorite(1)
        verify(catalogRepository).getFavMovies()
        assertNotNull(movies)
        assertEquals(10, movies.value?.size)

        viewModel.getListFavorite(1).observeForever(observer)
        verify(observer).onChanged(dummyFavorites)
    }
}