package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.service.GetMeService
import com.dmm.bootcamp.yatter2024.ui.post.PostUiState
import com.dmm.bootcamp.yatter2024.usecase.post.PostStatusUseCase
import com.dmm.bootcamp.yatter2024.usecase.profile.ProfileStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel (private val profileStatusUseCase: ProfileStatusUseCase,
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

            //val snapshotBindingModel = uiState.value.bindingModel
            _uiState.update {
                it.copy(
                    //bindingModel = snapshotBindingModel.copy(avatarUrl = me?.avatar?.toString()),
                    isLoading = false,
                )
            }
        }
    }

    fun onClickNavIcon() {}

    fun onCompleteNavigation() {}
    }