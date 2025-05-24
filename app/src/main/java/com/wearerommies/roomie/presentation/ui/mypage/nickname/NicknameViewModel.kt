package com.wearerommies.roomie.presentation.ui.mypage.nickname

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
class NicknameViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    // state 관리
    private val _state = MutableStateFlow(NicknameState())
    val state: StateFlow<NicknameState>
        get() = _state.asStateFlow()

    // side effect 관리
    private val _sideEffect: MutableSharedFlow<NicknameSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<NicknameSideEffect>
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
            _sideEffect.emit(NicknameSideEffect.NavigateToBookMark)
        }
    }
}