package com.wearerommies.roomie.presentation.ui.mypage.gender

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearerommies.roomie.domain.entity.GenderEntity
import com.wearerommies.roomie.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
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

    fun initGender(gender: String) {
        _state.value = _state.value.copy(
            gender = gender,
            updatedGender = gender
        )
    }

    fun updateGender(gender: String) {
        _state.value = _state.value.copy(
            updatedGender = gender,
        )
    }

    fun navigateUp() = viewModelScope.launch {
        _sideEffect.emit(GenderSideEffect.NavigateUp)
    }

    fun editUserGender() = viewModelScope.launch {
        userRepository.editUserGender(
            gender = GenderEntity(
                gender = state.value.updatedGender
            )
        )
            .onSuccess { response ->
                _state.value = _state.value.copy(
                    gender = response.gender,
                    updatedGender = response.gender
                )
                navigateUp()
            }
            .onFailure { error ->
                Timber.e(error)
            }
    }
}