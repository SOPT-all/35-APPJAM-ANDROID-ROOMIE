package com.wearerommies.roomie.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearerommies.roomie.R
import com.wearerommies.roomie.domain.entity.TourEntity
import com.wearerommies.roomie.domain.repository.HouseRepository
import com.wearerommies.roomie.presentation.core.util.UiState
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
class DetailViewModel @Inject constructor(
    private val houseRepository: HouseRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState>
        get() = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<DetailSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<DetailSideEffect>
        get() = _sideEffect.asSharedFlow()

    suspend fun getHouseDetail(houseId: Long) {
        houseRepository.getHouseDetail(houseId = houseId).onSuccess { response ->
            val updatedResponse = response.copy(
                rooms = response.rooms.map { room ->
                    room.copy(contractPeriod = room.contractPeriod ?: "-")
                }
            )
            _state.value = _state.value.copy(
                uiState = UiState.Success(updatedResponse)
            )
        }.onFailure { error ->
            Timber.e(error)
        }
    }

    fun updateBottomSheetState() {
        _state.value = _state.value.copy(
            isShowBottomSheet = !_state.value.isShowBottomSheet
        )
    }

    fun updateLivingExpanded() {
        _state.value = _state.value.copy(
            isLivingExpanded = !_state.value.isLivingExpanded
        )
    }

    fun updatedKitchenExpanded() {
        _state.value = _state.value.copy(
            isKitchenExpanded = !_state.value.isKitchenExpanded
        )
    }

    fun navigateToDetail(houseId: Long, roomId: Long, title: String) = viewModelScope.launch {
        _sideEffect.emit(
            DetailSideEffect.NavigateDetailRoom(
                houseId = houseId,
                roomId = roomId,
                title = title
            )
        )
    }

    fun navigateUp() = viewModelScope.launch {
        _sideEffect.emit(DetailSideEffect.NavigateUp)
    }

    fun navigateToHouse(houseId: Long, title: String) = viewModelScope.launch {
        _sideEffect.emit(DetailSideEffect.NavigateDetailHouse(houseId = houseId, title = title))
    }

    fun navigateToTourApply(houseId: Long, roomId: Long, houseName: String, roomName: String) =
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isShowBottomSheet = !_state.value.isShowBottomSheet
            )
            _sideEffect.emit(
                DetailSideEffect.NavigateTourApply(
                    tourEntity = TourEntity(
                        houseId = houseId,
                        roomId = roomId
                    ),
                    houseName = houseName,
                    roomName = roomName
                )
            )
        }

    fun updateSelectedTourRoomId(roomId: Long) {
        _state.value = _state.value.copy(
            selectedTourRoom = roomId
        )
    }

    fun updateSelectedTourRoomName(name: String) {
        _state.value = _state.value.copy(
            selectedTourRoomName = name
        )
    }

    fun bookmarkHouse(houseId: Long) = viewModelScope.launch {
        houseRepository.bookmarkHouse(houseId = houseId)
            .onSuccess { response ->
                if (response.isPinned.not()) {
                    _sideEffect.emit(
                        DetailSideEffect.SnackBar(
                            message = R.string.delete_at_bookmark_list
                        )
                    )
                }
                getHouseDetail(houseId = houseId)
            }
            .onFailure { error ->
                Timber.e(error)
            }
    }

    fun navigateToChannel() = viewModelScope.launch {
        _sideEffect.emit(DetailSideEffect.NavigateToChannel)
    }
}