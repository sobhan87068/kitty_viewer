package com.example.kittyviewer.ui.details

import androidx.lifecycle.viewModelScope
import com.example.kittyviewer.base.BaseViewModel
import com.example.kittyviewer.data.model.Kitty
import com.example.kittyviewer.domain.AddBookmarkUseCase
import com.example.kittyviewer.domain.GetIsBookmarkedUseCase
import com.example.kittyviewer.domain.RemoveBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val getIsBookmarkedUseCase: GetIsBookmarkedUseCase
) : BaseViewModel<DetailsAction>() {

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked = _isBookmarked.asStateFlow()
    override fun handleAction(action: DetailsAction) {
        when (action) {
            is DetailsAction.AddBookmark -> addBookmark(action.kitty)

            is DetailsAction.RemoveBookmark -> removeBookmark(action.kitty)

            is DetailsAction.GetBookmarkState -> getBookmarkState(action.kitty)
        }
    }

    private fun removeBookmark(kitty: Kitty) {
        viewModelScope.launch {
            removeBookmarkUseCase(kitty)
        }
    }

    private fun addBookmark(kitty: Kitty) {
        viewModelScope.launch {
            addBookmarkUseCase(kitty)
        }
    }

    private fun getBookmarkState(kitty: Kitty) {
        viewModelScope.launch {
            getIsBookmarkedUseCase(kitty).collect {
                _isBookmarked.emit(it)
            }
        }
    }
}