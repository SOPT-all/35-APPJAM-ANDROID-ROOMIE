package com.wearerommies.roomie.presentation.ui.mypage.contact

import androidx.lifecycle.ViewModel
import com.wearerommies.roomie.domain.repository.UserRepository
import com.wearerommies.roomie.presentation.core.util.RegexConstants.PHONE_NUMBER_REGEX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    // state 관리
    private val _state = MutableStateFlow(ContactState())
    val state: StateFlow<ContactState>
        get() = _state.asStateFlow()

    // side effect 관리
    private val _sideEffect: MutableSharedFlow<ContactSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<ContactSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun updatedPhoneNumber(phoneNumber: String) {
        _state.value = _state.value.copy(
            uiState = _state.value.uiState.copy(
                phoneNumber = phoneNumber
            )
        )

        _state.value = _state.value.copy(
            isValidated = PHONE_NUMBER_REGEX.matches(phoneNumber)
        )
    }
}