package com.dmm.bootcamp.yatter2024.ui.update

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.service.GetMeService
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileDestination
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineDestination
import com.dmm.bootcamp.yatter2024.ui.update.bindingmodel.ProfileUpdateBindingModel
import com.dmm.bootcamp.yatter2024.usecase.update.UpdateAccountUseCase
import com.dmm.bootcamp.yatter2024.usecase.update.UpdateAccountUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URL

class UpdateProfileViewModel(
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val getMeService: GetMeService,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UpdateProfileUiState> =
        MutableStateFlow(UpdateProfileUiState.empty())
    val uiState: StateFlow<UpdateProfileUiState> = _uiState.asStateFlow()

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized

    fun onCreate() {
        viewModelScope.launch {
            _isInitialized.value = false
            _uiState.update { it.copy(isLoading = true) }
            val me = getMeService.execute()
            Log.d("ProfileUpdateViewModel", "me.username: ${me?.username?.value}")
            Log.d("ProfileUpdateViewModel", "me.displayName: ${me?.displayName}")
            if (me != null) {
                _uiState.update {
                    it.copy(
                        username = me.username.value,
                        profileUpdateBindingModel = ProfileUpdateBindingModel(
                            displayName = me.displayName,
                            note = me.note,
                            avatar = me.avatar,
                            header = me.header
                        )
                    )
                }
            }
            _uiState.update { it.copy(isLoading = false) }
            _isInitialized.value = true
        }
    }

    fun onChangedDisplayName(displayName: String) {
        val snapshotBindingModel = uiState.value.profileUpdateBindingModel
        _uiState.update {
            it.copy(
                profileUpdateBindingModel = snapshotBindingModel.copy(
                    displayName = displayName
                )
            )
        }
    }

    fun onChangedNote(note: String) {
        val snapshotBindingModel = uiState.value.profileUpdateBindingModel
        _uiState.update {
            it.copy(
                profileUpdateBindingModel = snapshotBindingModel.copy(
                    note = note
                )
            )
        }
    }

    fun onChangedAvatar(avatar: URL) {
        val snapshotBindingModel = uiState.value.profileUpdateBindingModel
        _uiState.update {
            it.copy(
                profileUpdateBindingModel = snapshotBindingModel.copy(
                    avatar = avatar
                )
            )
        }
    }

    fun onChangedHeader(header: URL) {
        val snapshotBindingModel = uiState.value.profileUpdateBindingModel
        _uiState.update {
            it.copy(
                profileUpdateBindingModel = snapshotBindingModel.copy(
                    header = header
                )
            )
        }
    }

    fun onClickProfileUpdate() {
        viewModelScope.launch {
            Log.d("ProfileUpdateViewModel", "onClickProfileUpdate")
            _uiState.update { it.copy(isLoading = true) } // 1
            val snapBindingModel = uiState.value.profileUpdateBindingModel
            when (
                val result =
                    updateAccountUseCase.execute(
                        snapBindingModel.displayName,
                        snapBindingModel.note,
                        snapBindingModel.avatar,
                        snapBindingModel.header,
                    )
            ) {
                is UpdateAccountUseCaseResult.Success -> {
                    val me = getMeService.execute()
                    if (me?.username?.value != null) {
                        ProfileDestination.createRoute(me.username.value)
                        _destination.value = ProfileDestination()
                    } else {
                        _destination.value = PublicTimelineDestination()
                    }
                }
                is UpdateAccountUseCaseResult.Failure -> {
                    // 4
                    // エラー表⽰
                }
            }
            _uiState.update { it.copy(isLoading = false) } // 5
        }
    }

    fun onCompleteNavigation() {
        _destination.value = null
    }
}