package com.edo979.presentation_common.state

sealed class UiState<out T : Any> {
    data object Loading : UiState<Nothing>()
    data class Error<out T : Any>(val errorMessage: String) : UiState<T>()
    data class Success<out T : Any>(val data: T) : UiState<T>()
}