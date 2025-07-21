package com.wearerommies.roomie.presentation.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearerommies.roomie.R
import com.wearerommies.roomie.domain.entity.FilterEntity
import com.wearerommies.roomie.domain.entity.FilterResultEntity
import com.wearerommies.roomie.domain.entity.SearchResultEntity
import com.wearerommies.roomie.domain.repository.HouseRepository
import com.wearerommies.roomie.domain.repository.MapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
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
class MapViewModel @Inject constructor(
    private val mapRepository: MapRepository,
    private val houseRepository: HouseRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MapState())
    val state: StateFlow<MapState>
        get() = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<MapSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<MapSideEffect>
        get() = _sideEffect.asSharedFlow()

    fun fetchInitialLocation(longitude: Float?, latitude: Float?) {
        _state.value = _state.value.copy(
            latitude = latitude,
            longitude = longitude
        )
    }

    fun fetchFilterAndSearch(filter: FilterEntity, searchResult: SearchResultEntity) {
        _state.value = _state.value.copy(
            filter = _state.value.filter.copy(
                moodTag = filter.moodTag,
                depositRange = filter.depositRange,
                monthlyRentRange = filter.monthlyRentRange,
                genderPolicy = filter.genderPolicy,
                preferredDate = filter.preferredDate,
                occupancyTypes = filter.occupancyTypes,
                contractPeriod = filter.contractPeriod,
                location = searchResult.address.ifEmpty { filter.location },
                latitude = searchResult.latitude,
                longitude = searchResult.longitude
            ),
            searchResult = _state.value.searchResult.copy(
                location = searchResult.location,
                address = searchResult.address,
                roadAddress = searchResult.roadAddress,
                latitude = searchResult.latitude,
                longitude = searchResult.longitude
            )
        )
    }

    fun updateIsFull() {
        _state.value = _state.value.copy(
            isFullSelected = !_state.value.isFullSelected
        )
    }

    suspend fun fetchHouseList() {
        mapRepository.getFilterResult(_state.value.filter)
            .onSuccess { resultList ->
                _state.value = _state.value.copy(
                    latitude = resultList.latitude,
                    longitude = resultList.longitude,
                    houseList =
                    if (_state.value.isFullSelected)
                        resultList.house.filter { it.excludeFull }.toPersistentList()
                    else
                        resultList.house.map {
                            FilterResultEntity.HouseEntity(
                                houseId = it.houseId,
                                latitude = it.latitude,
                                longitude = it.longitude,
                                monthlyRent = it.monthlyRent,
                                deposit = it.deposit,
                                occupancyTypes = it.occupancyTypes,
                                location = it.location,
                                genderPolicy = it.genderPolicy,
                                locationDescription = it.locationDescription,
                                isPinned = it.isPinned,
                                moodTag = it.moodTag,
                                contractTerm = it.contractTerm,
                                mainImgUrl = it.mainImgUrl,
                                excludeFull = it.excludeFull
                            )
                        }.toPersistentList()
                )
            }.onFailure { error ->
                Timber.e(error)
            }
    }

    fun showMarkerDetail(id: Long) {
        val house = _state.value.houseList.find { it.houseId == id }

        if (house != null) {
            _state.value = _state.value.copy(
                markerDetail = house,
                clickedMarkerId = id
            )
        }
    }

    fun navigateToDetail(houseId: Long) = viewModelScope.launch {
        _sideEffect.emit(
            MapSideEffect.NavigateToDetail(
                houseId = houseId
            )
        )
    }

    fun navigateToSearch() = viewModelScope.launch {
        _sideEffect.emit(
            MapSideEffect.NavigateToSearch
        )
    }

    fun navigateToFilter() = viewModelScope.launch {
        _sideEffect.emit(
            MapSideEffect.NavigateToFilter(
                searchResult = _state.value.searchResult,
                filter = _state.value.filter
            )
        )
    }

    fun resetClickedMarker() {
        _state.value = _state.value.copy(
            clickedMarkerId = null
        )
    }

    fun setBottomSheetState(state: Boolean) {
        _state.value = _state.value.copy(
            isBottomSheetOpened = state
        )
    }

    fun bookmarkHouse(houseId: Long) = viewModelScope.launch {
        houseRepository.bookmarkHouse(houseId = houseId)
            .onSuccess { response ->

                _state.value = _state.value.copy(
                    markerDetail = _state.value.markerDetail.copy(
                        isPinned = !_state.value.markerDetail.isPinned
                    )
                )

                if (response.isPinned.not()) {
                    _sideEffect.emit(
                        MapSideEffect.SnackBar(
                            message = R.string.delete_at_bookmark_list
                        )
                    )
                }

                fetchHouseList()
            }.onFailure { error ->
                Timber.e(error)
            }
    }
}
