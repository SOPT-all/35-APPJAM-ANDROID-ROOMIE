package com.wearerommies.roomie.presentation.ui.mypage.birth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearerommies.roomie.domain.repository.UserRepository
import com.wearerommies.roomie.presentation.core.util.toFormattedString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BirthViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    // state 관리
    private val _state = MutableStateFlow(BirthState())
    val state: StateFlow<BirthState>
        get() = _state.asStateFlow()

    // side effect 관리
    private val _sideEffect: MutableSharedFlow<BirthSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<BirthSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun updatedBirthdate(birthdate: Long?) {
        _state.value = _state.value.copy(
            uiState = _state.value.uiState.copy(
                birthDate = birthdate?.let {
                    Date(birthdate).toFormattedString()
                } ?: ""
            )
        )
    }

    fun updateBirthDateModalState() = viewModelScope.launch {
        _state.value = _state.value.copy(
            isShowBirthDateModal = !_state.value.isShowBirthDateModal
        )
    }
}