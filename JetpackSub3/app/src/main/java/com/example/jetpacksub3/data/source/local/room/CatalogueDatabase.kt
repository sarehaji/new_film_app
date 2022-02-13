package com.example.jetpacksub3.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetpacksub3.data.source.local.entity.DataMovie
import com.example.jetpacksub3.data.source.local.entity.DataShow

@Database(entities = [DataMovie::class, DataShow::class], version = 1, exportSchema = false)
abstract class CatalogueDatabase : RoomDatabase(){
    abstract fun catalogueDao(): CatalogueDao

    companion object{
        @Volatile
        private var INSTANCE: CatalogueDatabase? = null

        fun getInstance(context: Context): CatalogueDatabase = INSTANCE ?: synchronized(this){
            INSTANCE ?: Room.databaseBuilder(context.applicationContext,
            CatalogueDatabase::class.java,
            "Catalogue.db").build()
        }
    }

}