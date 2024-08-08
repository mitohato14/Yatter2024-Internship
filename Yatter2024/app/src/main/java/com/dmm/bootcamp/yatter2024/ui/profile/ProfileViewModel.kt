package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.lifecycle.ViewModel
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.common.navigation.PopBackDestination
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.domain.service.GetMeService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel(
    private val username: String?,
    private val accountRepository: AccountRepository,
    private val getMeService: GetMeService,
    private val statusRepository: StatusRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState.empty())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    fun onCreate() {}

    fun onClickNavIcon() {
        _destination.value = PopBackDestination
    }
    fun onCompleteNavigation() {
        _destination.value = null
    }

}