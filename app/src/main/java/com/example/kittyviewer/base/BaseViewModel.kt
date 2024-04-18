package com.example.kittyviewer.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<A : ViewAction> : ViewModel() {

    private val _actions = Channel<A>(Channel.UNLIMITED)
    val actions = _actions.receiveAsFlow()
    protected abstract fun handleAction(action: A)

    init {
        viewModelScope.launch {
            actions.collect {
                handleAction(it)
            }
        }
    }

    fun submitAction(action: A) {
        viewModelScope.launch {
            _actions.send(action)
        }
    }
}