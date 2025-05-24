package com.wearerommies.roomie.presentation.ui.mypage.myaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearerommies.roomie.domain.entity.MyPageEntity
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

    suspend fun getUserInformation() {
        userRepository.getUserInformation()
            .onSuccess { response ->
                _state.value = _state.value.copy(
                    uiState = MyPageEntity(
                        name = response.name
                    )
                )
            }.onFailure { error ->
                Timber.e(error)
            }
    }


    fun navigateToBookmark() {
        viewModelScope.launch {
            _sideEffect.emit(MyAccountSideEffect.NavigateToBookMark)
        }
    }
}