package com.example.kittyviewer.ui.home

sealed class ApiState {
    data object Loading : ApiState()

    data object Idle : ApiState()

    data class Error(val message: String) : ApiState()
}