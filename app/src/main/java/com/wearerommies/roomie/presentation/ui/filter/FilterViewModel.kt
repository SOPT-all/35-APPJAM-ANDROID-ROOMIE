package com.wearerommies.roomie.presentation.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearerommies.roomie.presentation.core.util.toFormattedString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
) : ViewModel() {
    private val _state = MutableStateFlow(FilterState())
    val state: StateFlow<FilterState>
        get() = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<FilterSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<FilterSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun setDateModalVisible() {
        _state.value = _state.value.copy(
            isDateModalOpened = !_state.value.isDateModalOpened
        )
    }

    fun setDepositRangeStart(value: String) {
        _state.value = _state.value.copy(
            depositStart = value.toIntOrNull() ?: 0
        )
//        뷰모델에서 범위 관리 - 초기화/max값 넘었을때 갑설정
//        depositStart = when {
//            value.isEmpty() -> return
//            value.toInt() > 500 -> 500
//            else -> value.toInt()
//        }
    }

    fun setDepositRangeEnd(value: String) {
        _state.value = _state.value.copy(
            depositEnd = value.toIntOrNull() ?: 0
        )
    }

    fun setMonthlyRangeStart(value: String) {
        _state.value = _state.value.copy(
            monthlyRentStart = value.toIntOrNull() ?: 0
        )
    }

    fun setMonthlyRangeEnd(value: String) {
        _state.value = _state.value.copy(
            monthlyRentEnd = value.toIntOrNull() ?: 150
        )
    }

    fun setGenderPolicy(value: PersistentList<String>) {
        _state.value = _state.value.copy(
            genderPolicy = value
        )
    }

    fun setPreferredDate(value: Long?) {
        _state.value = _state.value.copy(
            preferredDate = value?.let {
                Date(it).toFormattedString()
            } ?: ""
        )
    }

    fun setOccupancyType(value: PersistentList<String>) {
        _state.value = _state.value.copy(
            occupancyType = value
        )
    }

    fun setContractPeriod(value: PersistentList<String>) {
        _state.value = _state.value.copy(
            contractType = value
        )
    }

    fun resetAll() {
        _state.value = _state.value.copy(
            location = "",
            moodTag = "",
            depositStart = 0,
            depositEnd = 500,
            monthlyRentStart = 0,
            monthlyRentEnd = 150,
            genderPolicy = persistentListOf(),
            preferredDate = "",
            occupancyType = persistentListOf(),
            contractType = persistentListOf()
        )
    }

    fun applyCondition() = viewModelScope.launch {
        _sideEffect.emit(FilterSideEffect.navigateToMap)
//        houseRepository.bookmarkHouse(houseId = houseId)
//            .onSuccess { bookmarkState ->
//                if (bookmarkState) {
//                    _sideEffect.emit(
//                        HomeSideEffect.SnackBar(
//                            message = R.string.add_to_bookmark_list
//                        )
//                    )
//                }
//                getHomeData()
//            }.onFailure { error ->
//                Timber.e(error)
//            }
    }
}
