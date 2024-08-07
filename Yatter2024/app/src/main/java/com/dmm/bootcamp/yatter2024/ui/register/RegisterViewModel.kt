package com.dmm.bootcamp.yatter2024.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.ui.login.LoginDestination
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterAccountUseCase
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterAccountUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerAccountUseCase: RegisterAccountUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<RegisterUiState> =
        MutableStateFlow(RegisterUiState.empty())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    fun onChangedUsername(username: String) {
        val snapshotBindingModel = uiState.value.registerBindingModel
        _uiState.update {
            it.copy(
                validUsername = Username(username).validate(),
                registerBindingModel = snapshotBindingModel.copy(
                    username = username
                )
            )
        }
    }

    fun onChangedPassword(password: String) {
        val snapshotBindingModel = uiState.value.registerBindingModel
        _uiState.update {
            it.copy(
                validPassword = Password(password).validate(),
                registerBindingModel = snapshotBindingModel.copy(
                    password = password
                )
            )
        }
    }

    fun onClickRegister() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) } // 1
            val snapBindingModel = uiState.value.registerBindingModel
            when (
                val result = registerAccountUseCase.execute(
                        snapBindingModel.username,
                        snapBindingModel.password,
                    ) // 2
            ) {
                is RegisterAccountUseCaseResult.Success -> {
                    // 3
                    _destination.value = LoginDestination()
                }
                is RegisterAccountUseCaseResult.Failure -> {
                    // 4
                    val errorMessage = when (result) {
                        is RegisterAccountUseCaseResult.Failure.EmptyUsername -> "ユーザー名が空です。"
                        is RegisterAccountUseCaseResult.Failure.EmptyPassword -> "パスワードが空です。"
                        is RegisterAccountUseCaseResult.Failure.InvalidPassword -> "無効なパスワードです。"
                        is RegisterAccountUseCaseResult.Failure.CreateAccountError -> "アカウントの作成に失敗しました: ${result.throwable.message}"
                        is RegisterAccountUseCaseResult.Failure.LoginError -> "ログインに失敗しました: ${result.throwable.message}"
                    }
                    _uiState.update { it.copy(errorMessage = errorMessage) }
                }
            }
            _uiState.update { it.copy(isLoading = false) } // 5
        }
    }

    fun onCompleteNavigation() {
        _destination.value = null
    }
}