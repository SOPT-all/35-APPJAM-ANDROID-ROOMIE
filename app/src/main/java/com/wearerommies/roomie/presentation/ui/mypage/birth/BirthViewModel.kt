package com.wearerommies.roomie.presentation.ui.mypage.birth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearerommies.roomie.domain.entity.BirthEntity
import com.wearerommies.roomie.domain.repository.UserRepository
import com.wearerommies.roomie.presentation.core.util.toFormattedDto
import com.wearerommies.roomie.presentation.core.util.toFormattedString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
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

    fun initBirth(birth: String) {
        _state.value = _state.value.copy(
            birth = birth,
            updatedBirth = birth
        )
    }

    fun updateBirthdate(birthdate: Long?) {
        _state.value = _state.value.copy(
            updatedBirth = birthdate?.let {
                Date(birthdate).toFormattedString()
            }?.toFormattedDto() ?: "",
        )
    }

    fun updateBirthDateModalState() = viewModelScope.launch {
        _state.value = _state.value.copy(
            isShowBirthDateModal = !_state.value.isShowBirthDateModal
        )
    }

    fun navigateUp() = viewModelScope.launch {
        _sideEffect.emit(BirthSideEffect.NavigateUp)
    }

    fun editUserBirth() = viewModelScope.launch {
        userRepository.editUserBirth(
            birthDay = BirthEntity(
                birthDay = state.value.updatedBirth
            )
        )
            .onSuccess { response ->
                _state.value = _state.value.copy(
                    birth = response.birthDay,
                    updatedBirth = response.birthDay
                )
                navigateUp()
            }
            .onFailure { error ->
                Timber.e(error)
            }
    }
}