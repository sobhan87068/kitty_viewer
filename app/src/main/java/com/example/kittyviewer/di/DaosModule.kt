package com.example.kittyviewer.di

import com.example.kittyviewer.database.KittyCache
import com.example.kittyviewer.database.dao.BookmarkDao
import com.example.kittyviewer.database.dao.KittyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun provideKittyDao(database: KittyCache): KittyDao = database.kittyDao()

    @Provides
    fun provideBookmarkDao(database: KittyCache): BookmarkDao = database.bookmarkDao()
}