package com.example.kittyviewer.ui.bookmark

import com.example.kittyviewer.base.ViewAction

sealed class BookmarkAction : ViewAction {
    data object GetBookmarks : BookmarkAction()
}