package com.example.kittyviewer.ui.details

sealed class DetailsState {
    data class BookmarkInfo(val isBookmarked: Boolean)
}