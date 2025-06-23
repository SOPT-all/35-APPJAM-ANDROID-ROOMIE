package com.wearerommies.roomie.presentation.ui.mypage.name

import androidx.lifecycle.ViewModel
import com.wearerommies.roomie.domain.repository.UserRepository
import com.wearerommies.roomie.presentation.core.util.RegexConstants.NAME_REGEX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    // state 관리
    private val _state = MutableStateFlow(NameState())
    val state: StateFlow<NameState>
        get() = _state.asStateFlow()

    // side effect 관리
    private val _sideEffect: MutableSharedFlow<NameSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<NameSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun initName(name: String) {
        _state.value = _state.value.copy(
            name = name,
            updatedName = name
        )
    }

    fun updateName(name: String) {
        _state.value = _state.value.copy(
            updatedName = name,
        )

        _state.value = _state.value.copy(
            isValidated = NAME_REGEX.matches(name)
        )
    }
}