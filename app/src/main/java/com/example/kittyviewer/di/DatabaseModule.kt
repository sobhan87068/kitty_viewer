package com.example.kittyviewer.di

import android.content.Context
import androidx.room.Room
import com.example.kittyviewer.database.KittyCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesKittyCache(
        @ApplicationContext context: Context,
    ): KittyCache = Room.databaseBuilder(
        context,
        KittyCache::class.java,
        "kitties-database",
    ).build()
}