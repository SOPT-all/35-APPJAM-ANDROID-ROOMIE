package com.wearerommies.roomie.presentation.ui.mypage.myaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearerommies.roomie.domain.entity.AccountEntity
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
class MyAccountViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    // state 관리
    private val _state = MutableStateFlow(MyAccountState())
    val state: StateFlow<MyAccountState>
        get() = _state.asStateFlow()

    // side effect 관리
    private val _sideEffect: MutableSharedFlow<MyAccountSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<MyAccountSideEffect>
        get() = _sideEffect.asSharedFlow()

    suspend fun getUserAccountInformation() {
        userRepository.getUserAccountInformation()
            .onSuccess { response ->
                _state.value = _state.value.copy(
                    uiState = AccountEntity(
                        nickname = response.nickname,
                        socialType = response.socialType,
                        birthDate = response.birthDate,
                        gender = response.gender,
                        name = response.name,
                        phoneNumber = response.phoneNumber
                    )
                )
            }.onFailure { error ->
                Timber.e(error)
            }
    }

    fun navigateToName(name: String) {
        viewModelScope.launch {
            _sideEffect.emit(
                MyAccountSideEffect.NavigateToName(
                    name = name
                )
            )
        }
    }

    fun navigateToNickname(nickname: String) {
        viewModelScope.launch {
            _sideEffect.emit(
                MyAccountSideEffect.NavigateToNickname(
                    nickname = nickname
                )
            )
        }
    }

    fun navigateToBirth(birth: String) {
        viewModelScope.launch {
            _sideEffect.emit(
                MyAccountSideEffect.NavigateToBirth(
                    birth = birth
                )
            )
        }
    }

    fun navigateToGender(gender: String) {
        viewModelScope.launch {
            _sideEffect.emit(
                MyAccountSideEffect.NavigateToGender(
                    gender = gender
                )
            )
        }
    }

    fun navigateToContact(contact: String) {
        viewModelScope.launch {
            _sideEffect.emit(
                MyAccountSideEffect.NavigateToContact(
                    contact = contact
                )
            )
        }
    }
}