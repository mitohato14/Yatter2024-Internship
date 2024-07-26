package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.domain.service.GetMeService
import com.dmm.bootcamp.yatter2024.ui.edit.EditDestination
import com.dmm.bootcamp.yatter2024.ui.post.PostUiState
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.converter.StatusConverter
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineDestination
import com.dmm.bootcamp.yatter2024.usecase.post.PostStatusUseCase
import com.dmm.bootcamp.yatter2024.usecase.profile.ProfileStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel (
    private val statusRepository: StatusRepository,
    private val getMeService: GetMeService,
    ) : ViewModel() {
    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState.empty())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    fun onCreate() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val me = getMeService.execute()

            val snapshotBindingModel = uiState.value.statusBindingModel
            _uiState.update {
                it.copy(
                    statusBindingModel = snapshotBindingModel.copy(username = me?.username?.value?: ""),
                    isLoading = false,
                )

            }
        }
    }

   private suspend fun fetchMyTimeline() {
    val statusList = statusRepository.findAllPublic() // 1
    _uiState.update {
      it.copy(
        statusList = StatusConverter.convertToBindingModel(statusList), // 2
      )
    }
  }

    fun onResume() {
        viewModelScope.launch { // 1
            _uiState.update { it.copy(isLoading = true) } // 2
            fetchMyTimeline() // 3
            _uiState.update { it.copy(isLoading = false) } // 4
        }
    }

    fun onRefresh() {
        viewModelScope.launch { // 1
            _uiState.update { it.copy(isRefreshing = true) } // 2
            fetchMyTimeline() // 3
            _uiState.update { it.copy(isRefreshing = false) } // 4
        }
    }

    fun onClickHome() {
        _destination.value = PublicTimelineDestination()
    }

    fun onClickProfile() {
        _destination.value = ProfileDestination()
    }

    fun onClickEdit() {
        _destination.value = EditDestination()
    }

    fun onCompleteNavigation() {
        _destination.value = null
    }

    }