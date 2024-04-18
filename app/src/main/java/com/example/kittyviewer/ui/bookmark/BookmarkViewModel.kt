package com.example.kittyviewer.ui.bookmark

import androidx.lifecycle.viewModelScope
import com.example.kittyviewer.base.BaseViewModel
import com.example.kittyviewer.data.model.Kitty
import com.example.kittyviewer.domain.GetBookmarksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarksUseCase: GetBookmarksUseCase
) : BaseViewModel<BookmarkAction>() {

    private val _bookmarks: MutableStateFlow<List<Kitty>> = MutableStateFlow(listOf())
    val bookmarks = _bookmarks.asStateFlow()
    override fun handleAction(action: BookmarkAction) {
        retrieveBookmarks()
    }

    private fun retrieveBookmarks() {
        viewModelScope.launch {
            getBookmarksUseCase().collect {
                _bookmarks.emit(it)
            }
        }
    }
}