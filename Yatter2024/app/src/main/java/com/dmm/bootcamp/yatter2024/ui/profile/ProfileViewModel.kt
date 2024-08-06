package com.dmm.bootcamp.yatter2024.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.domain.service.GetMeService
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.converter.ProfileConverter
import com.dmm.bootcamp.yatter2024.ui.update.UpdateProfileDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel (
    private val accountRepository: AccountRepository,
    private val getMeService: GetMeService
) : ViewModel() {
    private val _uiState: MutableStateFlow<ProfileUiState> =
        MutableStateFlow(ProfileUiState.empty())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    private suspend fun fetchProfile(username: Username) {
        val account = accountRepository.findByUsername(username)
        if (account != null) {
            var profile = ProfileConverter.convertToBindingModel(account)
            _uiState.update {
                it.copy(
                    profile,
                )
            }
        }
    }

    fun onCreate(userName: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            _uiState.update { it.copy(username = userName) }
            val me = getMeService.execute()
            Log.d("ProfileViewModel", "me.username: ${me?.username?.value}")
            Log.d("ProfileViewModel", "userName: $userName")
            _uiState.update {
                if (me?.username?.value == userName) {
                    it.copy(
                        username = userName,
                        isMe = true,
                    )
                } else {
                    it.copy(
                        username = userName,
                        isMe = false,
                    )
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onResume() {
        viewModelScope.launch { // 1
            _uiState.update { it.copy(isLoading = true) }
            _uiState.value.username?.let { username ->
                Log.d("ProfileViewModel", "username: $username")
                fetchProfile(Username(username))
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onClickUpdate() {
        // Profile更新画面へ遷移
        _destination.value = UpdateProfileDestination()
    }

    fun onCompleteNavigation() {
        _destination.value = null
    }
}