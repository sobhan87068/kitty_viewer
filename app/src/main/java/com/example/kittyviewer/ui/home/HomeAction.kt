package com.example.kittyviewer.ui.home

import com.example.kittyviewer.base.ViewAction

sealed class HomeAction : ViewAction {
    data object LoadMore : HomeAction()
}