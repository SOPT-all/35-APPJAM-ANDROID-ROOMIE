package com.wearerommies.roomie.presentation.ui.tour.completed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TourCompletedStepViewModel @Inject constructor(

) : ViewModel() {
    private val _sideEffect = MutableSharedFlow<TourCompletedSideEffect>()
    val sideEffect: SharedFlow<TourCompletedSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun navigateUp() = viewModelScope.launch {
        _sideEffect.emit(TourCompletedSideEffect.NavigateUp)
    }

    fun navigateHome() = viewModelScope.launch {
        _sideEffect.emit(TourCompletedSideEffect.NavigateToHome)
    }

    fun navigateToMap() = viewModelScope.launch {
        _sideEffect.emit(TourCompletedSideEffect.NavigateToMap)
    }
}
