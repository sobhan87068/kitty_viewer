package com.example.kittyviewer.data.result

sealed class ApiResult {

    data object ApiLoading : ApiResult()
    data object ApiSuccess : ApiResult()

    data class ApiError(val message: String? = "") : ApiResult()
}