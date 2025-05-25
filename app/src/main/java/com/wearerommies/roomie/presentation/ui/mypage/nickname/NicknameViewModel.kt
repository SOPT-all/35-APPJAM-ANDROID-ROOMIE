package com.wearerommies.roomie.presentation.ui.mypage.nickname

import androidx.lifecycle.ViewModel
import com.wearerommies.roomie.domain.repository.UserRepository
import com.wearerommies.roomie.presentation.core.util.RegexConstants.NICK_NAME_REGEX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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

    fun updatedNickname(nickname: String) {
        _state.value = _state.value.copy(
            uiState = _state.value.uiState.copy(
                name = nickname
            )
        )

        _state.value = _state.value.copy(
            isValidated = NICK_NAME_REGEX.matches(nickname)
        )
    }
}