package com.dmm.bootcamp.yatter2024.ui.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.ui.post.PostDestination
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.dmm.bootcamp.yatter2024.ui.timeline.bindingmodel.converter.StatusConverter
import kotlinx.coroutines.launch

class PublicTimelineViewModel(
    private val statusRepository: StatusRepository,
)  : ViewModel() {
    private val _uiState: MutableStateFlow<PublicTimelineUiState> =
        MutableStateFlow(PublicTimelineUiState.empty())
    val uiState: StateFlow<PublicTimelineUiState> = _uiState.asStateFlow()
    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    private suspend fun fetchPublicTimeline() {
        val statusList = statusRepository.findAllPublic() // 1
        _uiState.update {
            it.copy(
                statusList = StatusConverter.convertToBindingModel(statusList), // 2
            )
        }
    }

    fun onClickHome() {
        _destination.value = PublicTimelineDestination()
    }

    fun onClickProfile(){
        _destination.value = ProfileDestination()
    }

    fun onClickPost() {
        _destination.value = PostDestination()
    }
    fun onCompleteNavigation() {
        _destination.value = null
    }

    fun onResume() {
        viewModelScope.launch { // 1
            _uiState.update { it.copy(isLoading = true) } // 2
            fetchPublicTimeline() // 3
            _uiState.update { it.copy(isLoading = false) } // 4
        }
    }
    fun onRefresh() {
        viewModelScope.launch { // 1
            _uiState.update { it.copy(isRefreshing = true) } // 2
            fetchPublicTimeline() // 3
            _uiState.update { it.copy(isRefreshing = false) } // 4
        }
    }
}