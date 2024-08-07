package com.dmm.bootcamp.yatter2024.ui.timelinedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.common.navigation.PopBackDestination
import com.dmm.bootcamp.yatter2024.domain.model.StatusId
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileDestination
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.converter.StatusConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TimelineDetailViewModel(
    private val id: StatusId,
    private val statusRepository: StatusRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<TimelineDetailUiState> =
        MutableStateFlow(TimelineDetailUiState.empty())
    val uiState: StateFlow<TimelineDetailUiState> = _uiState.asStateFlow()

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination : StateFlow<Destination?> = _destination.asStateFlow()

    private suspend fun fetchTimelineDetail() {
        val status = statusRepository.findById(id)
        if(status != null){
            _uiState.update {
                it.copy(
                    statusBindingModel = StatusConverter.convertToBindingModel(status)
                )
            }
        }
    }

    fun onResume(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            fetchTimelineDetail()
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onRefresh(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            fetchTimelineDetail()
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onClickAvatar(username: String) {
        _destination.value = ProfileDestination(username)
    }

    fun onClickNavIcon() {
        _destination.value = PopBackDestination
    }

    fun onCompleteNavigation() {
        _destination.value = null
    }
}