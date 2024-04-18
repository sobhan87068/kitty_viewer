package com.example.kittyviewer.domain

import com.example.kittyviewer.data.model.Kitty
import com.example.kittyviewer.data.repository.Repository
import javax.inject.Inject

class AddBookmarkUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(kitty: Kitty) {
        repository.addToBookmarks(kitty)
    }
}