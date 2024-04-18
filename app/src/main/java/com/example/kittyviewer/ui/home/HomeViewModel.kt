package com.example.kittyviewer.ui.home

import androidx.lifecycle.viewModelScope
import com.example.kittyviewer.base.BaseViewModel
import com.example.kittyviewer.data.result.ApiResult
import com.example.kittyviewer.domain.GetKittiesUseCase
import com.example.kittyviewer.domain.RetrieveKittiesPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getKittiesUseCase: GetKittiesUseCase,
    private val retrieveKittiesPageUseCase: RetrieveKittiesPageUseCase,
) : BaseViewModel<HomeAction>() {

    private var nextPage = 1
    private var job: Job? = null

    private val _apiState: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Idle)
    val apiState = _apiState.asStateFlow()

    val kittiesState: StateFlow<KittiesState> = getKittiesUseCase().map {
        KittiesState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = KittiesState(listOf())
    )

    override fun handleAction(action: HomeAction) {
        when (action) {
            HomeAction.LoadMore -> loadMore()
        }
    }

    private fun loadMore() {
        if (apiState.value is ApiState.Loading) return

        job?.cancel()
        job = viewModelScope.launch {
            retrieveKittiesPageUseCase(nextPage).map {
                when (it) {
                    ApiResult.ApiLoading -> {
                        ApiState.Loading
                    }

                    is ApiResult.ApiSuccess -> {
                        nextPage++
                        ApiState.Idle
                    }

                    is ApiResult.ApiError -> {
                        ApiState.Error(
                            it.message ?: "Something Went Wrong"
                        )
                    }
                }
            }.collect { apiState ->
                _apiState.emit(apiState)
            }
        }
    }
}