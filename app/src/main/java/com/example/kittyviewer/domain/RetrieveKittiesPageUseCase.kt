package com.example.kittyviewer.domain

import com.example.kittyviewer.data.repository.Repository
import com.example.kittyviewer.data.result.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveKittiesPageUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(page: Int): Flow<ApiResult> {
        return repository.syncKitties(page)
    }
}