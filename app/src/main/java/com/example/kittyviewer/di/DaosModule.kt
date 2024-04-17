package com.example.kittyviewer.di

import com.example.kittyviewer.database.KittyCache
import com.example.kittyviewer.database.dao.KittyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun provideMovieDao(database: KittyCache): KittyDao = database.kittyDao()
}