package com.wearerommies.roomie.presentation.core.util

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data object Failure : UiState<Nothing>()
}

sealed class EmptyUiState<out T> {
    data object Initial: EmptyUiState<Nothing>()
    data object Loading : EmptyUiState<Nothing>()
    data object Empty : EmptyUiState<Nothing>()
    data class Success<T>(val data: T) : EmptyUiState<T>()
    data object Failure : EmptyUiState<Nothing>()
}
