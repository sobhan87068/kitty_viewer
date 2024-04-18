package com.example.kittyviewer.data.repository

import com.example.kittyviewer.data.model.Kitty
import com.example.kittyviewer.data.result.ApiResult
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getKittiesList(): Flow<List<Kitty>>

    suspend fun addToBookmarks(kitty: Kitty)

    fun getBookmarks(): Flow<List<Kitty>>

    fun isBookmarked(id: String): Flow<Boolean>

    suspend fun removeFromBookmarks(kitty: Kitty)

    suspend fun syncKitties(page: Int): Flow<ApiResult>
}