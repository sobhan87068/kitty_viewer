package com.example.kittyviewer.di

import com.example.kittyviewer.data.repository.Repository
import com.example.kittyviewer.data.repository.RepositoryImpl
import com.example.kittyviewer.network.NetworkDataSource
import com.example.kittyviewer.network.retrofit.RetrofitNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindRepository(
        repository: RepositoryImpl
    ): Repository

    @Binds
    fun bindNetworkDataSource(
        retrofitNetwork: RetrofitNetwork
    ): NetworkDataSource
}