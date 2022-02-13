package com.example.jetpacksub3.di

import android.content.Context
import com.example.jetpacksub3.data.source.CatalogRepository
import com.example.jetpacksub3.data.source.local.LocalDataSource
import com.example.jetpacksub3.data.source.local.room.CatalogueDatabase
import com.example.jetpacksub3.data.source.remote.RemoteDataSource
import com.example.jetpacksub3.utils.Connection

object Injection {
    fun provideRepository(context: Context): CatalogRepository {
        val database = CatalogueDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance(Connection())
        val localDataSource = LocalDataSource.getInstance(database.catalogueDao())
        return CatalogRepository.getInstance(remoteDataSource, localDataSource)
    }
}