package com.example.kittyviewer.data.repository

import com.example.kittyviewer.data.model.Kitty
import com.example.kittyviewer.data.result.ApiResult
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getKittiesList(): Flow<List<Kitty>>

    suspend fun syncKitties(page: Int): Flow<ApiResult>
}