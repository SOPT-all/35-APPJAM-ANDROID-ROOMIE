package com.wearerommies.roomie.presentation.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearerommies.roomie.domain.entity.FilterEntity
import com.wearerommies.roomie.domain.entity.SearchResultEntity
import com.wearerommies.roomie.presentation.core.util.toFormattedDto
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

    fun fetchSearchAndFilter(
        filterEntity: FilterEntity,
        searchResultEntity: SearchResultEntity
    ) {
        _state.value = _state.value.copy(
            filterEntity = filterEntity,
            searchResultEntity = searchResultEntity
        )
    }

    fun setDateModalVisible() {
        _state.value = _state.value.copy(
            isDateModalOpened = !_state.value.isDateModalOpened
        )
    }

    fun setDepositRangeStart(value: String) {
        _state.value = _state.value.copy(
            depositStart = when {
                value.isEmpty() -> ""
                value.toInt() > 500 -> 0
                else -> value.toInt()
            }.toString()
        )
    }

    fun setDepositRangeEnd(value: String) {
        _state.value = _state.value.copy(
            depositEnd = when {
                value.isEmpty() -> ""
                value.toInt() > 500 -> 500
                else -> value.toInt()
            }.toString()
        )
    }

    fun setMonthlyRangeStart(value: String) {
        _state.value = _state.value.copy(
            monthlyRentStart = when {
                value.isEmpty() -> ""
                value.toInt() > 150 -> 0
                else -> value.toInt()
            }.toString()
        )
    }

    fun setMonthlyRangeEnd(value: String) {
        _state.value = _state.value.copy(
            monthlyRentEnd = when {
                value.isEmpty() -> ""
                value.toInt() > 150 -> 150
                else -> value.toInt()
            }.toString()
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

    fun setMoodType(value: PersistentList<String>) {
        _state.value = _state.value.copy(
            moodType = value
        )
    }

    fun setContractPeriod(value: PersistentList<Int>) {
        _state.value = _state.value.copy(
            contractType = value
        )
    }

    fun resetAll() {
        _state.value = _state.value.copy(
            location = "",
            moodTag = null,
            depositStart = "",
            depositEnd = "",
            monthlyRentStart = "",
            monthlyRentEnd = "",
            genderPolicy = persistentListOf(),
            preferredDate = "",
            occupancyType = persistentListOf(),
            contractType = persistentListOf()
        )
    }

    fun applyCondition() = viewModelScope.launch {
        _sideEffect.emit(
            FilterSideEffect.navigateToMap(
                filter = FilterEntity(
                    location = _state.value.location,
                    moodTag = _state.value.moodTag,
                    depositRange = FilterEntity.DepositRange(
                        min = _state.value.depositStart.toIntOrNull() ?: 0,
                        max = _state.value.depositEnd.toIntOrNull() ?: 500
                    ),
                    monthlyRentRange = FilterEntity.MonthlyRentRange(
                        min = _state.value.monthlyRentStart.toIntOrNull() ?: 0,
                        max = _state.value.monthlyRentEnd.toIntOrNull() ?: 150
                    ),
                    genderPolicy = _state.value.genderPolicy,
                    preferredDate = _state.value.preferredDate.toFormattedDto(),
                    occupancyTypes = _state.value.occupancyType,
                    contractPeriod = _state.value.contractType
                ),
                searchResult = _state.value.searchResultEntity ?: SearchResultEntity()
            )
        )
    }
}
