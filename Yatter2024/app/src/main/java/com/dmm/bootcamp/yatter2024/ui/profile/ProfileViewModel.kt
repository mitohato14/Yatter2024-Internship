package com.dmm.bootcamp.yatter2024.ui.profile

import android.provider.ContactsContract.Profile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.domain.service.GetMeService
import com.dmm.bootcamp.yatter2024.infra.pref.TokenPreferences
import com.dmm.bootcamp.yatter2024.ui.login.LoginDestination
import com.dmm.bootcamp.yatter2024.ui.post.PostDestination
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.StatusBindingModel
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.converter.StatusConverter
import com.dmm.bootcamp.yatter2024.ui.setting.SettingDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init

class ProfileViewModel(
    private val accountRepository: AccountRepository,
    private val statusRepository: StatusRepository,
    private val tokenPreferences: TokenPreferences,
    private val getMeService: GetMeService,
    val username: String = "",
) : ViewModel() {
    private val _uiState: MutableStateFlow<ProfileUiState> =
        MutableStateFlow(ProfileUiState.empty())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()
    companion object {
        const val EGG_IMG = "https://pbs.twimg.com/media/C8UHG9pUMAAoatJ.jpg"
        const val GREY_IMG = "https://cottoitalia.com/wp-content/uploads/2019/12/grey-brush-2.jpg"
    }
    init{
        viewModelScope.launch {
            val account = accountRepository.findByUsername(Username(username))
            val me = getMeService.execute()
            _uiState.update {
                it.copy(
                    profileBindingModel = ProfileBindingModel(
                        myname = me?.username?.value,
                        username = username,
                        numFollow = account?.followingCount,
                        numFollower = account?.followerCount,
                        numPost = statusRepository.findStatusByUsername(username).size,
                        avatar = account?.avatar?.toString() ?: EGG_IMG,
                        header = account?.header?.toString() ?: GREY_IMG,
                        id = account?.id?.value,
                    )
                )
            }
        }
    }
    private suspend fun fetchProfile(){
        val statusList = statusRepository.findStatusByUsername(uiState.value.profileBindingModel.username)
        _uiState.update{
            it.copy(
                statusList = StatusConverter.convertToBindingModel(statusList),
            )
        }
    }
    fun onResume() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true)}
            fetchProfile()
            _uiState.update { it.copy(isLoading = false) }
        }
    }
    fun onRefresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            fetchProfile()
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }
    fun onClickPost(){
        _destination.value = PostDestination()
    }
    fun onClickSetting(){
        _destination.value = SettingDestination()
    }
    fun onClickLogout(){
        tokenPreferences.clear()
        _destination.value = LoginDestination()
    }
    fun onClickFollow(){}
    fun onClickFollower(){}
    fun onCompleteNavigation() {
        _destination.value = null
    }
}