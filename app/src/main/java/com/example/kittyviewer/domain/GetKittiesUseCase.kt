package com.example.kittyviewer.domain

import com.example.kittyviewer.data.model.Kitty
import com.example.kittyviewer.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetKittiesUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<List<Kitty>> {
        return repository.getKittiesList()
    }
}