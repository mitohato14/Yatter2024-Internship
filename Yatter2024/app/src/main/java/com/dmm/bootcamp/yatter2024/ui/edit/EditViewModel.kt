package com.dmm.bootcamp.yatter2024.ui.edit

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.domain.service.GetMeService
import com.dmm.bootcamp.yatter2024.ui.login.LoginDestination
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileDestination
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileUiState
import com.dmm.bootcamp.yatter2024.ui.profile.bindingmodel.converter.StatusConverter
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineDestination
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditViewModel (
    private val editUseCase: EditUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<EditUiState> = MutableStateFlow(EditUiState.empty())
    val uiState: StateFlow<EditUiState> = _uiState.asStateFlow()

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    //テキストボックスにユーザーが文字を入力したときに、UiState内の値を入力された文字に更新する
    fun onChangedUsername(username: String) {
        val snapshotBindingModel = uiState.value.editBindingModel
        _uiState.update {
            it.copy(
                validUsername = Username(username).validate(),
                editBindingModel = snapshotBindingModel.copy(
                    username = username
                )
            )
        }
    }

    fun onChangedPassword(password: String) {
        val snapshotBindingModel = uiState.value.editBindingModel
        _uiState.update {
            it.copy(
                validPassword = Password(password).validate(),
                editBindingModel = snapshotBindingModel.copy(
                    password = password
                )
            )
        }
    }

    //編集ボタンを押した際の動作を記述。
    fun onClickEdit() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) } // 1

            val snapBindingModel = uiState.value.editBindingModel
            when (
                val result =
                    editUseCase.execute(
                        Username(snapBindingModel.username),
                        Password(snapBindingModel.password),
                    ) // 2
            ) {
                is EditUseCaseResult.Success -> {
                    // 3
                    // パブリックタイムライン画面に遷移する処理の追加
                    _destination.value = ProfileDestination()
                }

                is EditUseCaseResult.Failure -> {
                    // 4
                    // エラー表示
                    Log.d(TAG, "Edit, Error!")
                }

            }

            _uiState.update { it.copy(isLoading = false) } // 5
        }
    }

    fun onClickHome() {
        _destination.value = PublicTimelineDestination()
    }

    fun onClickProfile() {
        _destination.value = ProfileDestination()
    }

    fun onCompleteNavigation() {
        _destination.value = null
    }
}