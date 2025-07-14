package com.wearerommies.roomie.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wearerommies.roomie.R
import com.wearerommies.roomie.domain.entity.HomeDataEntity
import com.wearerommies.roomie.domain.entity.LocationEntity
import com.wearerommies.roomie.domain.entity.RoomCardEntity
import com.wearerommies.roomie.domain.repository.HouseRepository
import com.wearerommies.roomie.domain.repository.MapRepository
import com.wearerommies.roomie.domain.repository.UserRepository
import com.wearerommies.roomie.presentation.core.util.EmptyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val houseRepository: HouseRepository,
    private val mapRepository: MapRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState>
        get() = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<HomeSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<HomeSideEffect>
        get() = _sideEffect.asSharedFlow()

    suspend fun getHomeData() {
        userRepository.getHomeData()
            .onSuccess { response ->
                val homeData = response.let {
                    HomeDataEntity(
                        nickname = it.nickname,
                        location = it.location,
                        recentlyViewedHouses = it.recentlyViewedHouses.map { item ->
                            RoomCardEntity(
                                houseId = item.houseId,
                                monthlyRent = item.monthlyRent,
                                deposit = item.deposit,
                                occupancyType = item.occupancyType,
                                location = item.location,
                                genderPolicy = item.genderPolicy,
                                locationDescription = item.locationDescription,
                                isPinned = item.isPinned,
                                moodTag = item.moodTag,
                                contractTerm = item.contractTerm,
                                mainImgUrl = item.mainImgUrl
                            )
                        }
                    )
                }

                _state.value = _state.value.copy(uiState = homeData)

            }.onFailure { error ->
                Timber.e(error)
            }
    }

    fun navigateToBookmark() {
        viewModelScope.launch {
            _sideEffect.emit(HomeSideEffect.NavigateToBookMark)
        }
    }

    fun navigateToMap() = viewModelScope.launch {
        _sideEffect.emit(HomeSideEffect.NavigateToMap)
    }


    fun navigateToMood(moodTag: String) {
        viewModelScope.launch {
            _sideEffect.emit(
                HomeSideEffect.NavigateToMood(
                    moodTag = moodTag
                )
            )
        }
    }

    fun navigateToDetail(houseId: Long) = viewModelScope.launch {
        _sideEffect.emit(
            HomeSideEffect.NavigateToDetail(
                houseId = houseId
            )
        )
    }

    fun navigateToWebView(webViewUrl: String) = viewModelScope.launch {
        _sideEffect.emit(HomeSideEffect.NavigateToWebView(webViewUrl = webViewUrl))
    }

    fun bookmarkHouse(houseId: Long) = viewModelScope.launch {
        houseRepository.bookmarkHouse(houseId = houseId)
            .onSuccess { response ->
                if (response.isPinned.not()) {
                    _sideEffect.emit(
                        HomeSideEffect.SnackBar(
                            message = R.string.delete_at_bookmark_list
                        )
                    )
                }
                getHomeData()
            }.onFailure { error ->
                Timber.e(error)
            }
    }

    fun emitFinishSnackBar() = viewModelScope.launch {
        _sideEffect.emit(
            HomeSideEffect.SnackBar(
                message = R.string.exit_application
            )
        )
    }

    fun updateBottomSheetState() {
        _state.value = _state.value.copy(
            isShowBottomSheet = !_state.value.isShowBottomSheet
        )
    }

    fun setSearchKeyword(searchKeyWord: String) {
        _state.value = _state.value.copy(
            bottomSheetState = _state.value.bottomSheetState.copy(
                searchKeyword = searchKeyWord
            )
        )
    }

    fun fetchSearchResult(query: String) = viewModelScope.launch {
        _state.value = _state.value.copy(
            bottomSheetState = _state.value.bottomSheetState.copy(
                searchResults = EmptyUiState.Loading
            )
        )

        mapRepository.getSearchResult(query)
            .onSuccess { result ->
                _state.value = _state.value.copy(
                    bottomSheetState = _state.value.bottomSheetState.copy(
                        searchResults = if (result.isEmpty()) EmptyUiState.Empty else EmptyUiState.Success(
                            result.toPersistentList()
                        )
                    )
                )
            }
            .onFailure { error ->
                Timber.e(error)
            }
    }

    fun applyUserLocation(
        latitude: Float,
        longitude: Float,
        location: String,
    ) = viewModelScope.launch {
        userRepository.editUserLocation(
            location = LocationEntity(
                latitude = latitude,
                longitude = longitude,
                location = location
            )
        )
            .onSuccess {
                if(it.location.isEmpty()){
                    _sideEffect.emit(HomeSideEffect.SnackBar(R.string.location_bottom_sheet_error))
                } else {
                    getHomeData()

                    updateBottomSheetState()

                    _state.value = _state.value.copy(
                        bottomSheetState = LocationBottomSheetState()
                    )
                }
            }
            .onFailure { error ->
               Timber.e(error)
            }
    }

}
