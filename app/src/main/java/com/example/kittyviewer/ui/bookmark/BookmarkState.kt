package com.example.kittyviewer.ui.bookmark

import com.example.kittyviewer.data.model.Kitty

sealed class BookmarkState {
    data class BookmarkList(val list: List<Kitty>) : BookmarkState()
}