package com.wearerommies.roomie.presentation.ui.mypage.gender

import androidx.lifecycle.ViewModel
import com.wearerommies.roomie.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class GenderViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    // state 관리
    private val _state = MutableStateFlow(GenderState())
    val state: StateFlow<GenderState>
        get() = _state.asStateFlow()

    // side effect 관리
    private val _sideEffect: MutableSharedFlow<GenderSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<GenderSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun updatedGender(gender: String) {
        _state.value = _state.value.copy(
            uiState = _state.value.uiState.copy(
                gender = gender
            )
        )
    }
}