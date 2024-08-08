package com.dmm.bootcamp.yatter2024.ui.setting

import android.app.Activity
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.R
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.common.navigation.PopBackDestination
import com.dmm.bootcamp.yatter2024.domain.service.GetMeService
import com.dmm.bootcamp.yatter2024.usecase.setting.SettingStatusUseCase
import com.dmm.bootcamp.yatter2024.usecase.setting.SettingStatusUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.net.URL

class SettingViewModel (
    private val settingStatusUseCase: SettingStatusUseCase,
    private val getMeService: GetMeService,
): ViewModel(){
    private val _uiState: MutableStateFlow<SettingUiState> = MutableStateFlow(SettingUiState.empty())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    private val _callGetImageHeader = MutableStateFlow<Unit?>(null)
    val callGetImageHeader: StateFlow<Unit?> = _callGetImageHeader.asStateFlow()

    private val _callGetImageAvatar = MutableStateFlow<Unit?>(null)
    val callGetImageAvatar: StateFlow<Unit?> = _callGetImageAvatar.asStateFlow()
    fun onCreate() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true)}
            val me = getMeService.execute()
            val snapshotBindingModel = uiState.value.settingBindingModel
            _uiState.update {
                it.copy(
                    settingBindingModel = snapshotBindingModel.copy(
                        newNote = me?.note,
                        me = me,
                        newAvatar = me?.avatar.toString(),
                        newHeader = me?.header.toString(),
                        newDisplayName = me?.displayName,
                    ),
                    isLoading = false,
                )
            }
        }
    }
    fun onChangedDisplayNameText(displayNameText: String){
        _uiState.update { it.copy(settingBindingModel = uiState.value.settingBindingModel.copy(newDisplayName = displayNameText))}
    }
    fun onChangedNoteText(noteText: String){
        _uiState.update { it.copy(settingBindingModel = uiState.value.settingBindingModel.copy(newNote = noteText))}
    }
    fun onChangedAvatarText(avatarText: File){
        // ここに画像を取ってくる機能を追加
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            // Uri -> Image
            val me = getMeService.execute()
            val snapshotBindingModel = uiState.value.settingBindingModel
            val result = settingStatusUseCase.execute(
                newDisplayName = me?.displayName,
                me = me,
                newAvatar = avatarText,// 変更しない画像データをどうするか
                newHeader = null,
                newNote = me?.note
            )
            when(result) {
                SettingStatusUseCaseResult.Success -> {
                    _uiState.update{
                        it.copy(settingBindingModel = snapshotBindingModel.copy(
                            newHeader = me?.header.toString(),
                        ))
                    }
                }
                is SettingStatusUseCaseResult.Failure -> {

                }
            }


            _uiState.update{ it.copy(isLoading = false)}
        }

    }
    fun getImageHeader(){
        _callGetImageHeader.value = Unit
    }
    fun getImageAvatar(){
        _callGetImageAvatar.value = Unit
    }
    fun onChangedHeaderText(headerText: File){
        //画像データを取ってくる機能
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            // Uri -> Image
            val me = getMeService.execute()
            val snapshotBindingModel = uiState.value.settingBindingModel
            val result = settingStatusUseCase.execute(
                newDisplayName = me?.displayName,
                me = me,
                newAvatar = null, // 変更しない画像データをどうするか
                newHeader = headerText,
                newNote = me?.note
            )
            when(result) {
                SettingStatusUseCaseResult.Success -> {
                    _uiState.update{
                        val newMe = getMeService.execute()
                        it.copy(settingBindingModel = snapshotBindingModel.copy(
                            newHeader = newMe?.header.toString(),
                        ))
                    }
                }
                is SettingStatusUseCaseResult.Failure -> {

                }
            }

            _uiState.update{it.copy(isLoading = false)}


        }
    }
    fun onClickChanged(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val me = getMeService.execute()
            val result = settingStatusUseCase.execute(
                newDisplayName = (uiState.value.settingBindingModel.newDisplayName ?: me?.displayName),
                me = me,
                newAvatar = null,
                newHeader = null,
                newNote = (uiState.value.settingBindingModel.newNote ?: me?.note)
            )
            when(result) {
                SettingStatusUseCaseResult.Success -> {
                    _destination.value = PopBackDestination
                }
                is SettingStatusUseCaseResult.Failure -> {

                }
            }
            _uiState.update{ it.copy(isLoading = false)}
        }
    }
    fun onClickBackIcon(){
        _destination.value = PopBackDestination
    }
    fun onCompleteNavigation(){
        _destination.value = null
    }

}