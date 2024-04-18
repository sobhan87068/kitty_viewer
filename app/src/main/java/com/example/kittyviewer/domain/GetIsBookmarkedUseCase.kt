package com.example.kittyviewer.domain

import com.example.kittyviewer.data.model.Kitty
import com.example.kittyviewer.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIsBookmarkedUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(kitty: Kitty): Flow<Boolean> {
        return repository.isBookmarked(kitty.id)
    }
}