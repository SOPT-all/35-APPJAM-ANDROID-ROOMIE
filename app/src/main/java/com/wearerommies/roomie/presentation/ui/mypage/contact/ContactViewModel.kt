package com.wearerommies.roomie.presentation.ui.mypage.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearerommies.roomie.domain.entity.ContactEntity
import com.wearerommies.roomie.domain.repository.UserRepository
import com.wearerommies.roomie.presentation.core.util.RegexConstants.PHONE_NUMBER_REGEX
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

    fun initPhoneNumber(phoneNumber: String) {
        _state.value = _state.value.copy(
            phoneNumber = phoneNumber,
            updatedPhoneNumber = phoneNumber
        )
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _state.value = _state.value.copy(
            updatedPhoneNumber = phoneNumber,
            isValidated = PHONE_NUMBER_REGEX.matches(phoneNumber)
        )
    }

    fun navigateUp() = viewModelScope.launch {
        _sideEffect.emit(ContactSideEffect.NavigateUp)
    }

    fun editUserContact() = viewModelScope.launch {
        userRepository.editUserContact(
            phoneNumber = ContactEntity(
                phoneNumber = state.value.updatedPhoneNumber
            )
        )
            .onSuccess { response ->
                _state.value = _state.value.copy(
                    phoneNumber = response.phoneNumber,
                    updatedPhoneNumber = response.phoneNumber
                )
                navigateUp()
            }
            .onFailure { error ->
                Timber.e(error)
            }
    }
}