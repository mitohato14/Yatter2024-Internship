package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.common.navigation.PopBackDestination
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.ui.post.PostDestination
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.converter.ProfileConverter
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.converter.StatusConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.filterList

class ProfileViewModel(
    private val username: Username,
    private val statusRepository: StatusRepository,
    private val accountRepository: AccountRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState.empty())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination : StateFlow<Destination?> = _destination.asStateFlow()

    private suspend fun fetchProfile() {
        val statusList = statusRepository.findAllPublic().filter{it.account.username == username}
        val accountList = accountRepository.findByUsername(username)
        if(accountList != null){
            _uiState.update {
                it.copy(
                    statusList = StatusConverter.convertToBindingModel(statusList),
                    profileBindingModel = ProfileConverter.convertToBindingModel(accountList)
                )
            }
        }
    }

    fun onResume() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            fetchProfile()
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onReFresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            fetchProfile()
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }

    fun onClickPost() {
        _destination.value = PostDestination()
    }

    fun onClickNavIcon() {
        _destination.value = PopBackDestination
    }

    fun onCompleteNavigation() {
        _destination.value = null
    }
}