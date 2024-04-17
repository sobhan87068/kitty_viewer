package com.example.kittyviewer.network

import com.example.kittyviewer.network.model.RemoteKitty

interface NetworkDataSource {
    suspend fun getKittiesList(page: Int): Result<List<RemoteKitty>>
}