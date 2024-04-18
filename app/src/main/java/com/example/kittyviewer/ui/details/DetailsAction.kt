package com.example.kittyviewer.ui.details

import com.example.kittyviewer.base.ViewAction
import com.example.kittyviewer.data.model.Kitty

sealed class DetailsAction : ViewAction {
    data class AddBookmark(val kitty: Kitty) : DetailsAction()
    data class RemoveBookmark(val kitty: Kitty) : DetailsAction()

    data class GetBookmarkState(val kitty: Kitty) : DetailsAction()
}